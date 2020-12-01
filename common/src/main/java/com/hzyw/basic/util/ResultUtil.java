package com.hzyw.basic.util;

import com.alibaba.fastjson.JSON;
import com.hzyw.basic.exception.Response;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@ControllerAdvice
public class ResultUtil implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, @Nullable Class converterType) {
        AnnotatedElement annotatedElement = returnType.getAnnotatedElement();
        AutoResult autoResult = AnnotationUtils.findAnnotation(annotatedElement, AutoResult.class);
        return (autoResult != null);
    }

    /**
     * 在这个方法完成 "怎样封装结果集"
     */
    @Override
    public Object beforeBodyWrite(Object returnValue, @Nullable MethodParameter returnType, @Nullable MediaType selectedContentType,
                                  @Nullable Class selectedConverterType, @Nullable ServerHttpRequest request, @Nullable ServerHttpResponse response) {
        if (!(returnValue instanceof Response)) {
            Response responseResult = new Response<>(returnValue);
            //因为handler处理类的返回类型是String，为了保证一致性，这里需要将ResponseResult转回去
            if (returnValue instanceof String) {
                return JSON.toJSONString(responseResult);
            }
            return responseResult;
        }
        return returnValue;
    }
}