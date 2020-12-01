package com.hzyw.basic.common.authentication;


import com.hzyw.basic.dos.ProjectEquipmentDO;
import com.hzyw.basic.util.*;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

@Intercepts({
        // @Signature(method = "query", type = Executor.class,args = { MappedStatement.class, Object.class,RowBounds.class, ResultHandler.class })
        //@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}) //2019.12.18
})
@Component
public class AuthSqlInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthSqlInterceptor.class);

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if(log.isInfoEnabled()){
            log.info("进入 AuthSqlInterceptor 拦截器...");
        }
        if(invocation.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
            StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
            //通过反射获取delegate父类BaseStatementHandler的mappedStatement属性
            MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
            // 获取方法上的数据权限注解，如果没有注解，则直接通过
            DataAuth dataAuth = getPermissionByDelegate(mappedStatement);
            if (dataAuth == null) {
                return invocation.proceed();
            }
            // 获取request信息，得到当前登录用户信息
            RequestAttributes req = RequestContextHolder.getRequestAttributes();
            if (req == null) {
                return invocation.proceed();
            }
            //处理request
            HttpServletRequest request = ((ServletRequestAttributes) req).getRequest();
            //获取用户
            String userName = JWTUtil.getUsername(TokenUtil.decryptToken(request.getHeader("Authentication")));
            if (StringUtils.isEmpty(userName)){
                throw new RuntimeException("无法从token中获取用户信息,请检查token时效性!");
            }
            //处理设备权限数据，并返回设备权限sql
            String orgAuthSql = this.dealOrgAuth(dataAuth,userName);

            //sql都为空，那直接返回
            if("".equals(orgAuthSql.trim())){
                return invocation.proceed();
            }
            log.info("待拼接sql：数据权限sql：" + orgAuthSql);
            BoundSql boundSql = delegate.getBoundSql();
            //原sql
            String sql = boundSql.getSql();
            //处理sql拼接
            ReflectUtil.setFieldValue(boundSql, "sql", permissionSql(sql,orgAuthSql));
        }
        //针对大屏插件多分装一层数据结构拆分
        operationScreen(invocation);
        return invocation.proceed();

    }


    /**
     * 处理设备权限数据，并返回设备权限sql
     * @param dataAuth
     * @param userName
     * @return
     */
    private String dealOrgAuth(DataAuth dataAuth, String userName) {
        String orgAuthSql = "";
        //设备编码字段注解
        String equipmentCode = dataAuth.equipmentCode();
        if (!StringUtils.isEmpty(equipmentCode)){
            List<ProjectEquipmentDO> list = PermissionUtil.getProjectDeviceByUserName(userName);
            for (ProjectEquipmentDO projectDeviceDO:list){
                if (!StringUtils.isEmpty(projectDeviceDO.getSqlPermission())){
                    orgAuthSql ="  pe."+equipmentCode + " in (" + projectDeviceDO.getSqlPermission() + ")  ";
                    break;
                }
            }
        }
        return orgAuthSql;
    }

    /**
     * 获取数据权限注解信息
     *
     * @param mappedStatement
     * @return
     */
    private DataAuth getPermissionByDelegate(MappedStatement mappedStatement) {
        DataAuth dataAuth = null;
        try {
            String id = mappedStatement.getId();
            String className = id.substring(0, id.lastIndexOf("."));
            String methodName = id.substring(id.lastIndexOf(".") + 1);
            final Class<?> cls = Class.forName(className);
            final Method[] method = cls.getMethods();
            for (Method me : method) {
                if (me.getName().equals(methodName) && me.isAnnotationPresent(DataAuth.class)) {
                    dataAuth = me.getAnnotation(DataAuth.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataAuth;
    }

    /**
     * 权限sql包装
     * @param sql 原sql
     * @param orgAuthSql 设备权限sql
     */
    private String permissionSql(String sql,String orgAuthSql) {
        //sql拼接

        if(!"".equals(orgAuthSql.trim())){
            // sql="select * from ("+sql+") pe where "+orgAuthSql;
            sql=sql.toUpperCase();
            if(sql.contains("LIMIT")){
                sql="select * from ("+sql;
                StringBuilder stringBuilder2=new StringBuilder(sql);
                int index = stringBuilder2.lastIndexOf("LIMIT");
                stringBuilder2.insert(index, ") pe where "+orgAuthSql);
                sql = stringBuilder2.toString();
            }else {
                sql="select * from ("+sql+") pe where "+orgAuthSql;
            }
        }
        return sql.toLowerCase();
    }

    /**
     * MappedStatement包装
     * @param ms
     * @param newSqlSource
     * @return
     */
    private MappedStatement newMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource,
                ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }


    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql,
                                      String sql, List<ParameterMapping> parameterMappings, Object parameter) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, parameterMappings, parameter);
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    private Object operationScreen(Invocation invocation) throws Throwable{
        //大屏拆件多分装了一层数据结构，需要单独拆分
        if (ReflectUtil.getFieldValue(invocation.getTarget(), "h")!=null){
            log.info("大屏数据拆分开始。。。。。");
            StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(ReflectUtil.getFieldValue(ReflectUtil.getFieldValue(invocation.getTarget(),"h"),"target"),"delegate");
            MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(ReflectUtil.getFieldValue(ReflectUtil.getFieldValue(ReflectUtil.getFieldValue(invocation.getTarget(), "h"), "target"), "delegate"), "mappedStatement");
            // 获取方法上的数据权限注解，如果没有注解，则直接通过
            DataAuth dataAuth = getPermissionByDelegate(mappedStatement);
            if (dataAuth == null) {
                return invocation.proceed();
            }
            // 获取request信息，得到当前登录用户信息
            RequestAttributes req = RequestContextHolder.getRequestAttributes();
            if (req == null) {
                return invocation.proceed();
            }
            //处理request
            HttpServletRequest request = ((ServletRequestAttributes) req).getRequest();
            //获取用户
            String userName = JWTUtil.getUsername(TokenUtil.decryptToken(request.getHeader("Authentication")));
            if (StringUtils.isEmpty(userName)){
                throw new RuntimeException("无法从token中获取用户信息,请检查token时效性!");
            }
            //处理设备权限数据，并返回设备权限sql
            String orgAuthSql = this.dealOrgAuth(dataAuth,userName);

            //sql都为空，那直接返回
            if("".equals(orgAuthSql.trim())){
                return invocation.proceed();
            }
            log.info("待拼接sql：数据权限sql：" + orgAuthSql);
            BoundSql boundSql = delegate.getBoundSql();
            //原sql
            String sql = boundSql.getSql();
            //处理sql拼接
            ReflectUtil.setFieldValue(boundSql, "sql", permissionSql(sql,orgAuthSql));
        }
        return invocation.proceed();

    }
}
