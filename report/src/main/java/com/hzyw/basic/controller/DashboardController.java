package com.hzyw.basic.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hzyw.basic.config.UrlConfig;
import com.hzyw.basic.exception.ErrorCodeAndMsg;
import com.hzyw.basic.exception.Response;
import com.hzyw.basic.exception.SystemException;
import com.hzyw.basic.service.DashboardService;
import com.hzyw.basic.service.ReportService;
import com.hzyw.basic.util.AutoResult;
import com.hzyw.basic.util.HttpUtils;
import com.hzyw.basic.vo.CountListVO;
import com.hzyw.basic.vo.OnNetCountVO;
import com.hzyw.basic.vo.ReportVO;
import com.hzyw.basic.vo.SearchVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author hao yuan
 * @date 2019.08.07
 */
@RestController
@RequestMapping(value = "/dashboard")
@Api(tags = "中控台v2.0，界面统计")
@Slf4j
public class DashboardController {

    @Resource
    private DashboardService dashboardService;
    
    
/*
    @Resource
    private UrlConfig urlConfig;*/


    /**
     * 统计每月一键告警数
     * 按月统计，统计当前月及历史各月的一键告警数，返回一年的统计
     */   
    @AutoResult
    @PostMapping("/stateAlarmNumMonthly")
    public List<JSONObject> stateAlarmNumMonthly(@RequestBody JSONObject searchVO) {//@RequestBody JSONObject searchVO
        if (searchVO == null || searchVO.get("projectId") == null) {
            throw new SystemException(ErrorCodeAndMsg.PARAMS_EMPTY);
        }
        return dashboardService.stateAlarmNumMonthly(searchVO);
    } 
    
    /**
     * 照明能耗
     * 按月统计照明能耗，统计当前月及历史各月的照明能耗，返回一年的统计
      * 
     */   
    @AutoResult
    @PostMapping("/stateLightEnergyMonthly")
    public List<JSONObject> stateLightEnergyMonthly(@RequestBody JSONObject searchVO) {//@RequestBody JSONObject searchVO
        if (searchVO == null || searchVO.get("projectId") == null) {
            throw new SystemException(ErrorCodeAndMsg.PARAMS_EMPTY);
        }
         return dashboardService.stateLightEnergyMonthly(searchVO);
    	//return new JSONObject();
    }
    
    /**
     * wifi访问量统计
     * 按月统计，统计当前月及历史各月的wifi登陆用户，返回一年的统计
     */
    @AutoResult
    @PostMapping("/stateWifiLogonUserMonthly")
    public List<JSONObject> stateWifiLogonUserMonthly(@RequestBody JSONObject searchVO) {//@RequestBody JSONObject searchVO
        if (searchVO == null || searchVO.get("projectId") == null) {
            throw new SystemException(ErrorCodeAndMsg.PARAMS_EMPTY);
        }
        return null;
        //return dashboardService.stateWifiLogonUserMonthly(searchVO);
     }
    
    /**
     * 告警统计
     * 统计各种设备类型下的告警数,返回当年的统计
     */
    @AutoResult
    @PostMapping("/stateExceptionNumMonthly")
    public List<JSONObject> stateExceptionNumMonthly(@RequestBody JSONObject searchVO) {//@RequestBody JSONObject searchVO
        if (searchVO == null || searchVO.get("projectId") == null) {
            throw new SystemException(ErrorCodeAndMsg.PARAMS_EMPTY);
        }
        return dashboardService.stateExceptionNumMonthly(searchVO);//unions:exception_t
     }
    
    /**
     * 设备统计
     * 统计各种设备类型的设备总数
     */
    @AutoResult
    @PostMapping("/stateDeviceNumByType")
    public List<JSONObject> stateDeviceNumByType(@RequestBody JSONObject searchVO) {//@RequestBody JSONObject searchVO
         if (searchVO == null || searchVO.get("projectId") == null) {
            throw new SystemException(ErrorCodeAndMsg.PARAMS_EMPTY);
        }
        //return null; //查询所有设备的表？
        return dashboardService.stateDeviceNumByType(searchVO); //查询所有设备的表？
    }
     
    /**
     * 在线率统计-中控台页面
     * -统计所有设备的在线状态，按设备类型来统计
     * @param searchVO
     * @return
     */
    @AutoResult
    @PostMapping("/statAllDevStatus")
    public List<JSONObject> statAllDevStatus(@RequestBody JSONObject searchVO) {//@RequestBody JSONObject searchVO
            if (searchVO == null || searchVO.get("projectId") == null) {
            throw new SystemException(ErrorCodeAndMsg.PARAMS_EMPTY);
        }
        //List<JSONObject> a =dashboardService.statAllDevStatus(searchVO);
        return dashboardService.statAllDevStatus(searchVO);
    }

    
    @AutoResult
    @PostMapping("/selectAllDevCodeByProjectId")
    public List<JSONObject> selectAllDevCodeByProjectId(@RequestBody JSONObject searchVO) {//@RequestBody JSONObject searchVO
         if (searchVO == null || searchVO.get("projectId") == null) {
            throw new SystemException(ErrorCodeAndMsg.PARAMS_EMPTY);
        } 
        return dashboardService.selectAllDevCodeByProjectId(searchVO);
    }

}