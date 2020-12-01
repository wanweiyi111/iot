package com.hzyw.basic.exception;

/**
 * 系统内部异常
 */
public class TokenException extends Exception {

    private static final long serialVersionUID = -994962710559017255L;

    public TokenException(String message) {
        super(message);
    }
}
