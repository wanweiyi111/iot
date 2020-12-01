package com.hzyw.basic.exception;

/**
 * @author male
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = -6370612186038915645L;

    private final CodeEnum response;

    public SystemException(CodeEnum response) {
        this.response = response;
    }

    public CodeEnum getResponse() {
        return response;
    }
}