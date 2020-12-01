package com.hzyw.basic.token.controller;

import com.hzyw.basic.service.AuDeptService;
import com.hzyw.basic.util.AutoResult;
import com.hzyw.basic.vo.DeptVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Validated
@RestController
@Api(value = "部门接口")
@RequestMapping("/dept")
public class DeptController {


    @Resource
    private AuDeptService deptService;

    /**
     * 获取部门所有信息
     *
     * @return
     */
    @GetMapping("/query/list")
    @ApiOperation(value = "获取部门信息")
    @AutoResult
    public List<DeptVO> queryUserListInfo() {
        return deptService.queryAllDeptInfo();
    }


}
