package com.hzyw.basic.token.controller;

import com.hzyw.basic.dos.RoleDO;
import com.hzyw.basic.service.RolePermissionService;
import com.hzyw.basic.service.RoleService;
import com.hzyw.basic.util.AutoResult;
import com.hzyw.basic.vo.PageVO;
import com.hzyw.basic.vo.ResponseVO;
import com.hzyw.basic.vo.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Validated
@RestController
@Api(value = "角色接口")
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @Resource
    private RolePermissionService rolePermissionService;

    @GetMapping("/query/queryRoleListInfo/{currentPage}/{pageSize}")
    @ApiOperation(value = "根据角色名称查询角色信息")
    @AutoResult
    public PageVO<RoleDO> queryRoleListInfo(@RequestParam("roleName") String roleName,
                                            @PathVariable("currentPage") int currentPage,
                                            @PathVariable("pageSize") int pageSize) {

        return roleService.selectByParameter(roleName, currentPage, pageSize);
    }

    @PostMapping("/addRole")
    @ApiOperation(value = "新增角色")
    //@RequiresPermissions(value = "role:add")
    @AutoResult
    public ResponseVO addRole(@RequestBody RoleVO role) {
        ResponseVO responseVO = new ResponseVO();
        RoleDO roleDO = new RoleDO();
        BeanUtils.copyProperties(role, roleDO);
        //插入角色
        //查询是否已存在该角色
        RoleDO roleInfo = roleService.findAuRoleInfo(roleDO);
        if (roleInfo != null) {
            responseVO.failure("该角色已存在");
            return responseVO;
        }
        roleService.insertAuRoleDO(role);
        //保存角色权限关联数据
        rolePermissionService.insertRolePermissionList(role.getRoleId(), role.getPermissionList());
        responseVO.success("新增角色成功!");
        return responseVO;
    }

    @PostMapping("/updateRole")
    @ApiOperation(value = "更新角色")
    //@RequiresPermissions("role:update")
    @AutoResult
    public ResponseVO updateRole(@RequestBody RoleVO role) {
        ResponseVO responseVO = new ResponseVO();
        RoleDO roleDO = new RoleDO();
        BeanUtils.copyProperties(role, roleDO);
        //修改角色
        roleService.updateByParameter(roleDO);
        //修改角色权限
        //先删除原来权限关系
        List<String> roles = Arrays.asList(String.valueOf(roleDO.getRoleId()));
        rolePermissionService.deleteByRoleId(roles);
        //再保存更改后的权限
        rolePermissionService.insertRolePermissionList(roleDO.getRoleId(), role.getPermissionList());
        responseVO.success("更改角色成功!");
        return responseVO;
    }

    @DeleteMapping("/deleteRole")
    @ApiOperation(value = "删除角色")
    //@RequiresPermissions("role:delete")
    @AutoResult
    public void deleteRoles(@NotBlank(message = "{required}") @RequestParam("roleIds") String roleIds) {
        roleService.deleteByParameter(roleIds);
    }

    /**
     * 获取角色信息
     *
     * @return
     */
    @GetMapping("/query/list")
    @ApiOperation(value = "获取全量角列表")
    @AutoResult
    public List<RoleDO> queryUserListInfo() {
        return roleService.queryAllRoleInfo();
    }

    @PostMapping("/query/checkRolePermissionById")
    @ApiOperation(value = "根据角色ID查看角色权限信息")
    @AutoResult
    public RoleVO checkRolePermissionById(@RequestBody RoleDO role) {
        return roleService.checkRolePermissionById(role);
    }

    @PostMapping("/query/getRolePermissionById")
    @ApiOperation(value = "根据角色ID获取角色权限信息")
    @AutoResult
    public RoleVO queryRoleById(@RequestBody RoleDO role) {
        return roleService.selectRoleById(role);
    }
}
