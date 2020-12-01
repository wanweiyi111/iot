package com.hzyw.basic.vo;


import lombok.Data;

@Data
public class PermissionVO {
    private Long id;
    private Long parentId;
    private String permissionName;
    private String  icon;
    private String path;
    private String  type;
    private Long orderNum;
    
}
