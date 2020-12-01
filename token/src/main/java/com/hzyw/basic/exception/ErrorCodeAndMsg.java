package com.hzyw.basic.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码定义
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeAndMsg implements CodeEnum {

    /**
     * 业务意义参见msg字段
     */
    username_error("0001", "用户名错误!"),
    username_lock("0002", "账号已被锁定,请联系管理员!"),
    password_error("0003", "密码错误!"),
    verificationCode_null("0004", "验证码不能为空!"),
    verificationCode_error("0005", "验证码错误,请重新输入!"),
    role_add_exist("1002","该角色已存在"),
     role_add_error("1001","新增角色失败"),
    role_update_error("1002","修改角色失败"),
    role_delete_error("1003","删除用户失败"),
    role_permission_error("1004","根据角色查询权限失败！");

    private String code;
    private String msg;
}