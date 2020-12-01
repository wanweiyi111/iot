package com.hzyw.basic.vo;

import com.hzyw.basic.dos.PermissionDO;
import com.hzyw.basic.dos.RoleDO;
import com.hzyw.basic.dos.RolePermissionDO;
import lombok.Data;

import java.util.List;

@Data
public class RoleVO extends RoleDO {
    /**
     *  角色与权限关系树状结构
     */
    private List<TreeVO<PermissionDO>> rolePermissionList;

    /*
    *角色与权限关系打平数据
     */
    private List<PermissionDO> permissions;

    private List<Integer> permissionList;

    private RolePermissionDO rolePermissionDO;
}
