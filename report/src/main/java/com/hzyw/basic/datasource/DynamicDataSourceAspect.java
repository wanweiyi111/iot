package com.hzyw.basic.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Aspect
@Order(-1)
@Component
@Slf4j
public class DynamicDataSourceAspect {

    @Pointcut("execution(* com.hzyw.basic.service.impl.*.*.list*(..))")
    public void pointCut() {
    }

    /**
     * 执行方法前更换数据源
     *
     * @param joinPoint        切点
     * @param targetDataSource 动态数据源
     */
    @Before("@annotation(targetDataSource)")
    public void doBefore(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        DataSourceKey dataSourceKey = targetDataSource.dataSourceKey();
        if (dataSourceKey == DataSourceKey.DB_LIGHT) {
            log.info(String.format("设置数据源为  %s", DataSourceKey.DB_LIGHT));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_LIGHT);

        } else if (dataSourceKey == DataSourceKey.DB_SCREEN) {
            log.info(String.format("设置数据源为  %s", DataSourceKey.DB_SCREEN));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SCREEN);

        } else if (dataSourceKey == DataSourceKey.DB_CAMERA) {
            log.info(String.format("设置数据源为  %s", DataSourceKey.DB_CAMERA));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_CAMERA);

        } else if (dataSourceKey == DataSourceKey.DB_WIFI) {
            log.info(String.format("设置数据源为  %s", DataSourceKey.DB_WIFI));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_WIFI);

        } else if (dataSourceKey == DataSourceKey.DB_SENSOR) {
            log.info(String.format("设置数据源为  %s", DataSourceKey.DB_SENSOR));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SENSOR);

        } else if (dataSourceKey == DataSourceKey.DB_RADAR) {
            log.info(String.format("设置数据源为  %s", DataSourceKey.DB_RADAR));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_RADAR);

        }else if (dataSourceKey == DataSourceKey.DB_CHARGER) {
            log.info(String.format("设置数据源为  %s", DataSourceKey.DB_CHARGER));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_CHARGER);

        } else if (dataSourceKey == DataSourceKey.DB_POLE) {
            log.info(String.format("设置数据源为  %s", DataSourceKey.DB_POLE));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_POLE);
        }
        else {
            log.info(String.format("使用默认数据源为  %s", DataSourceKey.DB_REPORT));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_REPORT);
        }
    }

    /**
     * 执行方法后清除数据源设置
     *
     * @param joinPoint        切点
     * @param targetDataSource 动态数据源
     */
    @After("@annotation(targetDataSource)")
    public void doAfter(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        log.info(String.format("当前数据源  %s  执行清理方法", targetDataSource.dataSourceKey()));
        DynamicDataSourceContextHolder.clear();
    }

    @Before(value = "pointCut()")
    public void doBeforeWithSlave(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取当前切点方法对象
        Method method = methodSignature.getMethod();
        if (method.getDeclaringClass().isInterface()) {//判断是否为借口方法
            try {
                //获取实际类型的方法对象
                method = joinPoint.getTarget().getClass()
                        .getDeclaredMethod(joinPoint.getSignature().getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                log.error("方法不存在！", e);
            }
        }
        if (null == method.getAnnotation(TargetDataSource.class)) {
            log.error("没有标记数据源注解：");
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_POLE);
        }
    }

}
