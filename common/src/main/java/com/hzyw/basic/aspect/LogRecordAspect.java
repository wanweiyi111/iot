/*
package com.hzyw.basic.aspect;

import com.alibaba.fastjson.JSONObject;
import com.hzyw.basic.config.Properties;
import com.hzyw.basic.domain.UserDO;
import com.hzyw.basic.util.HttpUtils;
import com.hzyw.basic.util.UserUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

*/
/**
 * @author male
 *//*

@Aspect
@Component
@Order(100)
public class LogRecordAspect {

    private static final String DEFAULT_IS_ASPECT = "undefined";
    private String operationResult = "成功";

    @SuppressWarnings({"EmptyMethod", "unused"})
    @Pointcut("execution(public * com.hzyw.basic..controller..*.*(..))")
    public void log() {}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        if (isAspect()) {
            JSONObject operationLog = getRecord(joinPoint);
            HttpUtils.doPost(Properties.LOG_URL + "/operations", operationLog.toJSONString(), false);
        }
    }

    @After("log()")
    public void doAfter(JoinPoint joinPoint) {
        if (isAspect()) {
            JSONObject applicationLog = getRecord(joinPoint);
            applicationLog.put("result", operationResult);
            applicationLog.put("type", Properties.APP_DESCRIPTION);
            HttpUtils.doPost(Properties.LOG_URL + "/applications", applicationLog.toJSONString(), false);
        }
    }

    @AfterThrowing(throwing = "throwable", pointcut = "log()")
    public void doAfterThrowing(Throwable throwable) {
        if (isAspect()) {
            operationResult = "失败";

            JSONObject exceptionObj = new JSONObject();
            exceptionObj.put("level", "错误");
            exceptionObj.put("source", Properties.APP_DESCRIPTION);
            exceptionObj.put("detail", throwable.toString());
            HttpUtils.doPost(Properties.LOG_URL + "/exceptions", exceptionObj.toJSONString(), false);
        }
    }

    private JSONObject getRecord(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        UserDO userDO = UserUtils.getCurrentUser();
        userDO = Optional.ofNullable(userDO).orElse(new UserDO());

        JSONObject record = new JSONObject();
        record.put("ip", HttpUtils.getRealClientIp());
        record.put("type", getOperationType(method.getName()));
        if(method.getAnnotation(ApiOperation.class) != null ){
        	record.put("record", method.getAnnotation(ApiOperation.class).value());
        }else{
        	record.put("record", "");
        }
        
        record.put("userId", userDO.getId());
        record.put("userName", userDO.getUserName());

        return record;
    }

    private String getOperationType(String methodName) {
        String operationType;
        switch (methodName.substring(0, 3)) {
            case "fin":
            case "get":
            case "lis": operationType = "查询"; break;
            case "cou": operationType = "统计"; break;
            case "add":
            case "sav":
            case "ins": operationType = "保存"; break;
            case "rem":
            case "del": operationType = "删除"; break;
            case "upd": operationType = "修改"; break;
            case "set": operationType = "设置"; break;
            default: operationType = "未知";
        }
        return operationType;
    }

    private boolean isAspect() {
        boolean isAspect = true;

        if (ArrayUtils.contains(Properties.NO_RECORD_APP, Properties.APP_NAME)) {
            isAspect = false;
        }

        if (!DEFAULT_IS_ASPECT.equals(Properties.IS_ASPECT_RECORD)) {
            isAspect = Boolean.valueOf(Properties.IS_ASPECT_RECORD);
        }

        return isAspect;
    }
}*/
