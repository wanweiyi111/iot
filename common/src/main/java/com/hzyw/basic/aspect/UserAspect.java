package com.hzyw.basic.aspect;

import com.hzyw.basic.config.Properties;
import com.hzyw.basic.domain.UserDO;
import com.hzyw.basic.domain.UserDTO;
import com.hzyw.basic.exception.ErrorCode;
import com.hzyw.basic.exception.SystemException;
import com.hzyw.basic.util.HttpUtils;
import com.hzyw.basic.util.UserUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author weianqi
 */
@Aspect
@Component
@Order(50)
public class UserAspect {

    private static final String URL_API = "/api";
    private static final String DEFAULT_IS_ASPECT = "undefined";

    @SuppressWarnings({"EmptyMethod", "unused"})
    @Pointcut("execution(public * com.hzyw.basic..controller..*.*(..))")
    public void invokMethod() {}

    @Before("invokMethod()")
    public void doBefore(JoinPoint joinPoint) {
        // 如果“不用做权限校验”的工程或者接口，直接跳过
        if (!isAspect()) {
            return;
        }

        // 判断当前访问，用户是否登录
        UserDTO userDTO = UserUtils.getCurrentUserDTO();
        if (userDTO == null) {
            throw new SystemException(ErrorCode.USER_NOT_LOGIN);
        }

        // 判断当前访问用户，是否具有访问权限
        boolean isHavaPermission = isHavePermission(HttpUtils.getCurrentRequest().getRequestURI(), userDTO.getPermissions());
        if (!isHavaPermission) {
            throw new SystemException(ErrorCode.USER_NO_PERMISSION);
        }

        // 根据业务规则，对请求参数进行处理
        updateRequestArgs(joinPoint, userDTO);
    }

    private void setValue(Object obj, String methodName, Object value, Class cl) {
        Class<?> c = obj.getClass();
        try {
            Method[] methods = c.getMethods();
            List<String> methodList = new ArrayList<>();
            for (Method method1 : methods) {
                methodList.add(method1.getName());
            }
            if (methodList.contains(methodName)) {
                Method method = c.getMethod(methodName, cl);
                method.invoke(obj, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查路径是否含有/api串
     * @param value URL路径
     * @return URI（"/api"路径后面的部分 ）
     */
    private String splitApi(String value) {
        if (StringUtils.isNotBlank(value) && value.contains(URL_API)) {
            value = value.split(URL_API)[1];
        }
        return value;
    }

    private boolean isHavePermission(String uri, List<String> permissionList) {
        uri = splitApi(uri);
        uri = uri.split("/[0-9]+")[0];
        uri = StringUtils.isNotBlank(uri) ? uri + "/" : uri;

        boolean isHavaPermission = false;
        for (String permission : permissionList) {
            permission = splitApi(permission);
            if (uri.equals(permission)) {
                isHavaPermission = true;
                break;
            }
        }

        return isHavaPermission;
    }

    private void updateRequestArgs(JoinPoint joinPoint, UserDTO userDTO) {
        Object[] argsObj = joinPoint.getArgs();
        if (ArrayUtils.isNotEmpty(argsObj)) {
            UserDO userDO = userDTO.getUser();
            String userName = userDO.getUserName();
            for (Object obj : argsObj) {
                if (obj == null) {
                    continue;
                }
                setValue(obj, "setCreateByName", userName, String.class);
                setValue(obj, "setUpdateByName", userName, String.class);
                setValue(obj, "setProjectId", userDO.getProjectId(), Long.class);
                setValue(obj, "setEquipmentCodeList", userDTO.getEquipmentcodes(), List.class);
            }
        }
    }

    private boolean isAspect() {
        boolean isAspect = true;

        if (ArrayUtils.contains(Properties.NO_AUTHORITY_APP, Properties.APP_NAME)) {
            isAspect = false;
        }

        if (!DEFAULT_IS_ASPECT.equals(Properties.IS_ASPECT_AUTHORITY)) {
            isAspect = Boolean.valueOf(Properties.IS_ASPECT_AUTHORITY);
        }

        if (Properties.SKIP_AUTH.equals(HttpUtils.getCurrentToken())) {
            isAspect = false;
        }

        return isAspect;
    }
}