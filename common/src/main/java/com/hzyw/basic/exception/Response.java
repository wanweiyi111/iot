package com.hzyw.basic.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hzyw.basic.util.HttpUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * @author male
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> implements Serializable {

    private static final long serialVersionUID = -4505655308965878999L;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应状态码
     */
    private String code;

    /**
     * 响应描述
     */
    private String msg;

    public Response() {
        this.code = HttpUtils.HTTP_OK;
        this.msg = "请求成功";
    }

    public Response(String code, String msg) {
        this();
        this.code = code;
        this.msg = msg;
    }

    @SuppressWarnings("unused")
    public Response(String code, String msg, T data) {
        this();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Response(T data) {
        this();
        this.data = data;
    }
}
