package com.hzyw.basic.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hao yuan
 * @date 2019.08.07
 */

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(SystemException.class)
    @ResponseBody
    public Response handleSystemException(HttpServletRequest request, SystemException ex) {
        Response response;
        log.error("SystemException code:{},msg:{}", ex.getResponse().getCode(), ex.getResponse().getMsg());
        response = new Response(ex.getResponse().getCode(), ex.getResponse().getMsg());
        return response;
    }
}

