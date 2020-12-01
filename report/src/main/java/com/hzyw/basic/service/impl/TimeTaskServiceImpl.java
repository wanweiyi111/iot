package com.hzyw.basic.service.impl;

import com.alibaba.fastjson.JSON;
import com.hzyw.basic.config.UrlConfig;
import com.hzyw.basic.dao.jpa.ReportRepository;
import com.hzyw.basic.dao.mapper.LightPowerReportDao;
import com.hzyw.basic.dao.mapper.ReportDao;
import com.hzyw.basic.dos.LightPowerReportDO;
import com.hzyw.basic.dos.ReportDO;
import com.hzyw.basic.exception.Response;
import com.hzyw.basic.util.Constant;
import com.hzyw.basic.util.HttpUtils;
import com.hzyw.basic.vo.OnNetCountVO;
import com.hzyw.basic.vo.ProjectPowerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Service
@Slf4j
public class TimeTaskServiceImpl {

    @Resource
    private ReportDao dao;

    @Resource
    private ReportRepository repository;

    @Resource
    ReportServiceImpl reportService;

    @Resource
    UrlConfig urlConfig;

    @Resource
    LightPowerReportDao lightPowerReportDao;

//    @Scheduled(cron = "59 57 14 * * ?")
    public void getCountByDay(List<ProjectPowerVO> projectPowerVOS) {
        //项目分组统计项目的基础数据，查询运行表查询
//        List<LightRuntimeDO> list = reportService.findTodayAllCount();
        //构建统计报表对象
        Set<Long> projectIds = new HashSet();
        //设备CODE列表
        Set<String> codeList = new HashSet();
        //拿到项目列表
        for (ProjectPowerVO projectPowerVO : projectPowerVOS) {
            projectIds.add(projectPowerVO.getProjectId());
            codeList.add(projectPowerVO.getEquipmentCode());
        }
        //根据项目获取统计报表
        for (ProjectPowerVO projectPowerVO : projectPowerVOS) {
            Long projectId = projectPowerVO.getProjectId();
            //计算器(每次进来归零)
            int failureCount = 0;
            int projectTotal = 0;
            int normalCount = 0;
            //查询灯基础数据表，获取设备总数
            Map<String, Long> projectIdData = new HashMap<>();
            projectIdData.put("projectId", projectId);
            String data = JSON.toJSONString(projectIdData);
            String listData = HttpUtils.doPost(urlConfig.getLightUrl(), data, false);
            Response response;
            response = JSON.parseObject(listData, Response.class);
//            if (response.getCode().equalsIgnoreCase(Response.getSuccessCode())) {
//                List<Object> data1 = (List<Object>) response.getData();
//                projectTotal = data1.size();
//            }
            if (HttpUtils.HTTP_OK.equals(response.getCode())) {
                Map<String, Object> data1 = (Map) response.getData();
                log.info(data1.toString());
                String data2 = JSON.toJSON(data1).toString();
                OnNetCountVO onNetCountVO = JSON.parseObject(data2, OnNetCountVO.class);
                failureCount=onNetCountVO.getErrorTotal();
                normalCount=onNetCountVO.getOnTotal();
                projectTotal=onNetCountVO.getAllTotal();
            }
//            //获取故障数
//            for (LightRuntimeDO r : list) {
//                if (id == r.getProjectId()) {
//                    //如果有一次故障则该设备当天的为故障
//                    if (Constant.Status.errorWork.equalsIgnoreCase(r.getEquipmentStatus())) {
//                        //如果设备列表没有(去同一设备多次故障场景)
//                        if ((!codeList.contains(r.getEquipmentCode()))) {
//                            failureCount++;
//                            codeList.add(r.getEquipmentCode());
//                        }
//                    }
//                }
//            }
//            normalCount = projectTotal - failureCount;
            ReportDO reportDO = new ReportDO();
            LightPowerReportDO powerReportDO = lightPowerReportDao.findEnergySaving(projectId);
            reportDO.setEnergySaving(powerReportDO.getSaveEnergy()==null?0:powerReportDO.getSaveEnergy());
            reportDO.setProjectId(projectId);
            reportDO.setProjectTotal(projectTotal);
            reportDO.setFailureCount(failureCount);
            reportDO.setNormalCount(normalCount);
            reportDO.setFailureRate(((failureCount + 0.000D) / projectTotal) * 100.0);
            reportDO.setLightingRate(((normalCount + 0.000D) / projectTotal) * 100.0);
            reportDO.setReportDate(new Date());
            reportDO.setEquipmentType(4112);
            reportDO.setReportType(Constant.ReportType.lightDay);
            //保存报表信息
            repository.save(reportDO);
        }

    }

//    @Scheduled(cron = "59 59 23 * * ?")
//    public void getCountByMonth() {
//        //项目分组统计项目的基础数据，查询运行表查询
//        List<LightRuntimeDO> list = reportService.findCountByMonth();
//        //构建统计报表对象
//        Set<Long> projectIds = new HashSet();
//        //设备CODE列表
//        Set<String> codeList = new HashSet();
//        //拿到项目列表
//        for (LightRuntimeDO runtimeDO : list) {
//            projectIds.add(runtimeDO.getProjectId());
//            codeList.add(runtimeDO.getEquipmentCode());
//        }
//        //根据项目获取统计报表
//        for (Long id : projectIds) {
//            //计算器(每次进来归零)
//            int failureCount = 0;
//            int projectTotal = 0;
//            int normalCount = 0;
//            //查询灯基础数据表，获取设备总数
//            Map<String, Long> projectIdData = new HashMap<>();
//            projectIdData.put("projectId", id);
//            String data = JSON.toJSONString(projectIdData);
//            String listData = HttpUtils.doPost(urlConfig.getLight_count_url(), data);
//            Response response = null;
//            response = JSON.parseObject(listData, Response.class);
//            if (response.getCode().equalsIgnoreCase(Response.getSuccessCode())) {
//                List<Object> data1 = (List<Object>) response.getData();
//                projectTotal = data1.size();
//            }
//            //获取故障数
//            for (LightRuntimeDO r : list) {
//                if (id == r.getProjectId()) {
//                    //如果有一次故障则该设备当天的为故障
//                    if (Constant.Status.errorWork.equalsIgnoreCase(r.getEquipmentStatus())) {
//                        //如果设备列表没有(去同一设备多次故障场景)
//                        if ((!codeList.contains(r.getEquipmentCode()))) {
//                            failureCount++;
//                            codeList.add(r.getEquipmentCode());
//                        }
//                    }
//                }
//            }
//            normalCount = projectTotal - failureCount;
//            ReportDO reportDO = new ReportDO();
//            reportDO.setEnergySaving(256.2330);
//            reportDO.setProjectId(id);
//            reportDO.setProjectTotal(projectTotal);
//            reportDO.setFailureCount(failureCount);
//            reportDO.setNormalCount(normalCount);
//            reportDO.setFailureRate(((failureCount + 0.000D) / projectTotal) * 100.0);
//            reportDO.setLightingRate(((normalCount + 0.000D) / projectTotal) * 100.0);
//            reportDO.setReportDate(new Date());
//            reportDO.setEquipmentType(list.get(0).getEquipmentType());
//            reportDO.setReportType(Constant.ReportType.Month);
//            //保存报表信息
//            repository.save(reportDO);
//        }
//    }
}
