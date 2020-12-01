package com.hzyw.basic.domain;

import lombok.Data;

import java.util.List;

/**
 * @author male
 */
@Data
public class UserDTO {
    /**可访问URL列表*/
    private List<String> permissions;

    /**用户信息*/
    private UserDO user;

    /**可操作设备列表*/
    private List<String> equipmentcodes;
}