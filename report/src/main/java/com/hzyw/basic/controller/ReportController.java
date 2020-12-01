package com.hzyw.basic.controller;

import com.alibaba.fastjson.JSON;
import com.hzyw.basic.config.UrlConfig;
import com.hzyw.basic.exception.ErrorCodeAndMsg;
import com.hzyw.basic.exception.Response;
import com.hzyw.basic.exception.SystemException;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping(value = "/info")
@Api(tags = "报表相关接口")
@Slf4j
public class ReportController {

    @Resource
    private ReportService service;

    @Resource
    private UrlConfig urlConfig;


    @AutoResult
    @PostMapping("/findByPercentage")
    @ApiOperation(value = "获取指定项目统计周月年报表")
    public List<ReportVO> findByPercentage(@RequestBody SearchVO searchVO) {
        if (searchVO == null || searchVO.getProjectId() == null) {
            throw new SystemException(ErrorCodeAndMsg.PARAMS_EMPTY);
        }
        return service.findByPercentage(searchVO);
    }

    @AutoResult
    @PostMapping("/findByOnNetCount")
    @ApiOperation(value = "根据项目id查询照明设备")
    public Map<String, OnNetCountVO> findByOnNetCount(@RequestBody SearchVO vo) {
        if (vo == null || (vo.getProjectId() == null)) {
            throw new SystemException(ErrorCodeAndMsg.PARAMS_EMPTY);
        }
        Map<String, Long> map = new HashMap<>();
        map.put("projectId", vo.getProjectId());
        String data = JSON.toJSON(map).toString();

        Map<String, OnNetCountVO> onNetCountVOS = new HashMap<>();
        String[] urls = new String[]{
                urlConfig.getLightUrl(),
                urlConfig.getScreenUrl(),
                urlConfig.getCameraUrl(),
//                urlConfig.getWifi_url(),
                urlConfig.getSensorUrl(),
                urlConfig.getRadarUrl(),
                urlConfig.getEtcUrl()};
        String[] keys = new String[]{"light", "screen", "camera", "sensor", "radar", "etc"};
        for (int i = 0; i < urls.length; i++) {
//            log.info("keys[i]:"+keys[i]+"   urls[i]:"+urls[i]);
            onNetCountVOS.put(keys[i], sendPost(urls[i], data));
        }
        return onNetCountVOS;
    }

    private OnNetCountVO sendPost(String url, String data) {

        OnNetCountVO onNetCountVO = null;
        Response response;
        String vo = HttpUtils.doPost(url, data,true);
        if (StringUtils.isEmpty(vo)) {
            return null;
        }
        response = JSON.parseObject(vo, Response.class);
        if (HttpUtils.HTTP_OK.equals(response.getCode())) {
            Map<String, Object> data1 = (Map) response.getData();
            if (CollectionUtils.isEmpty(data1)) {
                return null;
            }
            log.info(data1.toString());
            String data2 = JSON.toJSON(data1).toString();
            onNetCountVO = JSON.parseObject(data2, OnNetCountVO.class);
        }
        return onNetCountVO;
    }

    @AutoResult
    @PostMapping("/findByReportList")
    @ApiOperation(value = "获取指定项目信息")
    public CountListVO findByReportList(@RequestBody SearchVO searchVO) {
        if (searchVO == null || searchVO.getProjectId() == null) {
            throw new SystemException(ErrorCodeAndMsg.PARAMS_EMPTY);
        }
        return service.findByReportList(searchVO);
    }

    @AutoResult
    @PostMapping("/findByReportListMonth")
    @ApiOperation(value = "获取指定项目信息")
    public CountListVO findByReportListMonth(@RequestBody SearchVO searchVO) {
        if (searchVO == null || searchVO.getProjectId() == null) {
            throw new SystemException(ErrorCodeAndMsg.PARAMS_EMPTY);
        }
        return service.findByReportListMonth(searchVO);
    }

    @AutoResult
    @PostMapping("/findMonthOfYear")
    @ApiOperation(value = "获取指定项目信息")
    public CountListVO findMonthOfYear(@RequestBody SearchVO searchVO) {
        if (searchVO == null || searchVO.getProjectId() == null) {
            throw new SystemException(ErrorCodeAndMsg.PARAMS_EMPTY);
        }
        return service.findMonthOfYear(searchVO);
    }

}