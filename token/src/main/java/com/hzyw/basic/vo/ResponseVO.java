package com.hzyw.basic.vo;

import lombok.Data;


/**
 *返回状态对象
 */
@Data
public class ResponseVO {

    private String code;
    private String message;

    public void success(String message){
        this.code="200";
        this.message=message;
    }

    public void failure(String message){
        this.code="400";
        this.message=message;
    }
}
