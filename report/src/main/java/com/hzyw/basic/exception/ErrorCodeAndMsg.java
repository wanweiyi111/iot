package com.hzyw.basic.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hao yuan
 * @date 2019.08.07
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeAndMsg implements CodeEnum {
    ;

    private String code;
    private String msg;
}