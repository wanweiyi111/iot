package com.hzyw.basic.controller;

import com.hzyw.basic.exception.ErrorCodeAndMsg;
import com.hzyw.basic.exception.SystemException;
import com.hzyw.basic.service.LightPowerService;
import com.hzyw.basic.util.AutoResult;
import com.hzyw.basic.vo.ResPowerVO;
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
@RequestMapping(value = "/light/power")
@Api(tags = "灯能量报表相关接口")
@Slf4j
public class LightPowerController {

    @Resource
    private LightPowerService service;


    @AutoResult
    @PostMapping("/findByDimensionAndType")
    @ApiOperation(value = "获取指定项目统计周月年报表")
    public ResPowerVO findByDimensionAndType(@RequestBody SearchLightPowerVO searchVO) {
        if (searchVO == null || searchVO.getSearchType() == null
                ||searchVO.getSearchDimension()==null) {
            throw new SystemException(ErrorCodeAndMsg.PARAMS_EMPTY);
        }
        return service.findByDimensionAndType(searchVO);
    }

}