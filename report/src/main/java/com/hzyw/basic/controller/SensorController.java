package com.hzyw.basic.controller;

import com.hzyw.basic.exception.ErrorCodeAndMsg;
import com.hzyw.basic.exception.SystemException;
import com.hzyw.basic.service.SensorService;
import com.hzyw.basic.util.AutoResult;
import com.hzyw.basic.vo.ResSensorVO;
import com.hzyw.basic.vo.SearchLightPowerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author hao yuan
 * @date 2019.08.07
 */
@RestController
@RequestMapping(value = "/sensor/runtime")
@Api(tags = "灯能量报表相关接口")
@Slf4j
public class SensorController {

    @Resource
    private SensorService service;


    @AutoResult
    @PostMapping("/findByTimeDimension")
    @ApiOperation(value = "获取指定项目统计周月年报表")
    public ResSensorVO findByTimeDimension(@RequestBody SearchLightPowerVO searchVO) {
        if (searchVO == null ||searchVO.getSearchType()==null) {
            throw new SystemException(ErrorCodeAndMsg.PARAMS_EMPTY);
        }
        return service.findByTimeDimension(searchVO);
    }

}