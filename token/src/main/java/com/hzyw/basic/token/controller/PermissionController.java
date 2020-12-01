package com.hzyw.basic.token.controller;

import com.hzyw.basic.dos.PermissionDO;
import com.hzyw.basic.dos.ProjectDeviceDO;
import com.hzyw.basic.service.PermissionService;
import com.hzyw.basic.util.AutoResult;
import com.hzyw.basic.vo.PageVO;
import com.hzyw.basic.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@Api(value = "权限接口")
@RequestMapping("/menu")
public class PermissionController {

    @Resource
    private PermissionService promissionService;

    @GetMapping("/query/treeList")
    @ApiOperation(value = "查询菜单树形列表")
    @AutoResult
    public Map<String, Object> queryPermissionTreeListInfo() {
        return promissionService.selectMenuTreeList();
    }

    @PostMapping("/queryMethodPermissionList/{currentPage}/{pageSize}")
    @ApiOperation(value = "获取方法权限信息列表")
    @AutoResult
    public PageVO<PermissionDO> queryMethodPermissionList(@RequestBody(required = false) PermissionDO permission,
                                                          @PathVariable("currentPage") int currentPage,
                                                          @PathVariable("pageSize") int pageSize) {
        return promissionService.selectByParameter(permission, currentPage, pageSize);
    }

    @GetMapping("/query/list")
    @ApiOperation(value = "查询菜单列表")
    @AutoResult
    public List<PermissionDO> queryPermissionListInfo() {
        return promissionService.selectMenuList();
    }

    @PostMapping("/addPermission")
    @ApiOperation(value = "新增权限")
    // @RequiresPermissions(value = "permission:add")
    @AutoResult
    public ResponseVO addPermission(@RequestBody PermissionDO auPermissionDO) {
        return promissionService.insertAuPermissionDO(auPermissionDO);
    }

    @DeleteMapping("/deletePermission")
    @ApiOperation(value = "删除权限")
    //@RequiresPermissions("permission:delete")
    @AutoResult
    public void deletePermission(@NotBlank(message = "{required}") @RequestParam("promissionIds") String promissionIds) {
        promissionService.deleteByParameter(promissionIds);
    }

    @PutMapping("/updateMenu")
    @ApiOperation(value = "更新栏目")
    // @RequiresPermissions("menu:update")
    @AutoResult
    public ResponseVO updateMenu(@RequestBody PermissionDO menu) {
        return promissionService.updateByParameter(menu);
    }

    @GetMapping("/query/poleTreeList")
    @ApiOperation(value = "查询菜单树形列表")
    @AutoResult
    public Map<String, Object> queryPolePermissionTreeListInfo() {
        return promissionService.selectPoleMenuTreeList();
    }

    @PostMapping("/addProjectDevice")
    @ApiOperation(value = "新增配置用户查看项目设备数据权限")
    @AutoResult
    public ResponseVO addProjectDevice(@RequestBody List<ProjectDeviceDO> projectDeviceList) {
        return promissionService.addProjectDevice(projectDeviceList);
    }

    @GetMapping("/query/getProjectDevice")
    @ApiOperation(value = "获取用户配置设备权限信息")
    @AutoResult
    public List<String> getProjectDevice(@RequestParam("userName") String userName, @RequestParam("projectId") String projectId) {
        List<ProjectDeviceDO> projectDeviceDOS = promissionService.getProjectDeviceByUserName(userName, projectId);
        List<String> list = new ArrayList<>();
        for (ProjectDeviceDO projectDeviceDO : projectDeviceDOS) {
            String promission = projectDeviceDO.getEquipmentId();
            if (!StringUtils.isEmpty(promission)){
                list.add(promission);
            }
        }
        return list;
    }
}
