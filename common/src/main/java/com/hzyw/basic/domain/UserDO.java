package com.hzyw.basic.domain;

import lombok.Data;

/**
 * @author male
 */
@Data
public class UserDO {
    /**用户ID*/
    private Long id;

    /**用户名*/
    private String userName;

    /**部门ID*/
    private Integer deptId;

    /**项目ID*/
    private Long projectId;
}