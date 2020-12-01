package com.hzyw.basic.token.controller;


import com.hzyw.basic.dos.ProjectDO;
import com.hzyw.basic.service.ProjectService;
import com.hzyw.basic.util.AutoResult;
import com.hzyw.basic.util.TokenConstant;
import com.hzyw.basic.vo.PageVO;
import com.hzyw.basic.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Validated
@RestController
@Api(value = "项目接口")
@RequestMapping("/project")
public class ProjectController {

    @Resource
    private ProjectService projectService;

    /**
     * 获取部门所有信息
     *
     * @return
     */
    @GetMapping("/query/list")
    @ApiOperation(value = "获取项目信息")
    @AutoResult
    public List<ProjectDO> queryUserListInfo() {
        return projectService.selectAll();
    }

    @GetMapping("/queryList/{currentPage}/{pageSize}")
    @ApiOperation(value = "获取项目信息")
    @AutoResult
    public PageVO<ProjectDO> queryUserListInfo(@RequestParam("projectName") String projectName,
                                               @PathVariable("currentPage") int currentPage,
                                               @PathVariable("pageSize") int pageSize) {
        return projectService.queryAuProjectVOListInfo(projectName, currentPage, pageSize);
    }


    /**
     * 根据条件查询项目信息
     *
     * @param
     * @return
     */
    @PostMapping("/getProjectInfo")
    @ApiOperation(value = "根据项目名称查询项目信息")
    @AutoResult
    public List<ProjectDO> queryAuProjectVOInfo(@RequestBody ProjectDO project) {
        List<ProjectDO> list=new ArrayList<>();
        return this.projectService.queryAuProjectVOInfo(project);
    }


    @PostMapping("/addAuProject")
    @ApiOperation(value = "新增项目")
    @AutoResult
    public ResponseVO addProject(@RequestBody ProjectDO projectVO) {
        ResponseVO responseVO = new ResponseVO();
        String msg = validationProject(projectVO);
        if (StringUtils.isNotEmpty(msg)) {
            responseVO.setCode("400");
            responseVO.setMessage(msg);
            return responseVO;
        }
        List<ProjectDO> auProjectVO = projectService.queryAuProjectVOInfo(projectVO);
        if (!CollectionUtils.isEmpty(auProjectVO)) {
            responseVO.setCode("400");
            responseVO.setMessage("该项目已存在!");
            return responseVO;
        }
        projectService.insertProjectVO(projectVO);
        responseVO.setCode("200");
        responseVO.setMessage("新增项目成功!");
        return responseVO;
    }

    @DeleteMapping("/deleteProject")
    @ApiOperation(value = "删除项目")
    @AutoResult
    public ResponseVO deleteProject(@RequestParam("projectIds") String projectIds) {
        ResponseVO responseVO = new ResponseVO();
        if (StringUtils.isEmpty(projectIds)) {
            responseVO.setCode("400");
            responseVO.setMessage("项目ID为空!");
            return responseVO;
        }
        String[] ids = projectIds.split(TokenConstant.Common.SPLIT_COMMA);
        List<String> projectIdList = Arrays.asList(ids);
        projectService.deleteProjectByProjectId(projectIdList);
        responseVO.setCode("200");
        responseVO.setMessage("删除项目成功!");
        return responseVO;
    }

    @PostMapping("/updateProject")
    @ApiOperation(value = "修改项目")
    @AutoResult
    public ResponseVO updateProject(@RequestBody ProjectDO projectVO) {
        ResponseVO responseVO = new ResponseVO();
        String msg = validationProject(projectVO);
        if (StringUtils.isNotEmpty(msg)) {
            responseVO.setCode("400");
            responseVO.setMessage(msg);
            return responseVO;
        }
        projectService.updateAuProjectVO(projectVO);
        responseVO.setCode("200");
        responseVO.setMessage("修改项目成功!");
        return responseVO;
    }

    /**
     * 校验入参
     *
     * @param projectVO
     * @return
     */

    public String validationProject(ProjectDO projectVO) {
        String message = "";
        if (StringUtils.isEmpty(projectVO.getProjectName())) {
            return "项目名不能为空!";
        }
        if (StringUtils.isEmpty(projectVO.getCity())) {
            return "项目所在城市不能为空!";
        }
        if (StringUtils.isEmpty(projectVO.getDistrict())) {
            return "项目所在地区不能为空!";
        }
        if (StringUtils.isEmpty(projectVO.getStreet())) {
            return "项目所在街道不能为空!";
        }
        return message;
    }
}
