package com.hzyw.basic.service.impl;

import com.hzyw.basic.dao.jpa.ChargeReportRepository;
import com.hzyw.basic.dos.ChargeOrderDO;
import com.hzyw.basic.dos.ChargeReportDO;
import com.hzyw.basic.util.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author haoyuan
 * @date 2019.08.07
 */

/**
 * 描述：统计报表展示数据统计信息。按日、周、月、年，
 * 以时间为维度+全部或站点或充电桩维度展示充电次数统计、充电时长统计、充电电量统计、以及参数的汇总数量展示；
 * 操作：用户可按时间维度查询统计数据，用户可按设备维度查询统计数据，用户可按站点维度查询统计数据。
 * 查询条件：某日期 至  某日期查询；设备编号查询（可选择编号或输入编号）；
 * 站点编号查询（可选择站点或输入站点）下拉选择查询（最近7天、最近30天、最近365天）
 * 充电总次数：展示查询条件范围内，充电总次数；
 * 充电总时长：展示查询条件范围内，充电总时长；
 * 充电总电量：展示查询条件范围内，充电总电量；
 * 当统计时间大于1个月时，则按年+月份统计趋势图，反之当选择的统计时间小于1个月时，则按天数统计趋势图
 */
@Service
public class ChargeTimeTaskServiceImpl {


    @Resource
    ChargeReportRepository repository;

    @Resource
    ChargeServiceImpl service;

    @Scheduled(cron = "59 59 23 * * ?")
    public void getCountByDay() {

        //查询运行表查询
        List<ChargeOrderDO> list = service.findTodayCounts();
        //构建统计报表对象
        Set<Long> projectIds = new HashSet();
        //设备id列表
        Set<String> ids = new HashSet();
        //站点列表
        Set<String> stationIds = new HashSet();
        //拿到项目列表
        for (ChargeOrderDO chargeOrderDO : list) {
            projectIds.add(chargeOrderDO.getProjectId());
            ids.add(chargeOrderDO.getChargerId());
            stationIds.add(chargeOrderDO.getStationId());
        }

        for (String id:ids) {
            ChargeReportDO chargeReportDO=new ChargeReportDO();
           Integer totalNumber=0;
            long totalTime = 0L;
            double totalPower = 0D;
            for (ChargeOrderDO chargeOrderDO : list) {
                if (id.equalsIgnoreCase(chargeOrderDO.getChargerId())){
                    totalNumber++;
                    long endTime = chargeOrderDO.getEndTime().getTime();
                    long startTime = chargeOrderDO.getStartTime().getTime();
                    totalTime+=(endTime-startTime);
                    totalPower+=Double.parseDouble(chargeOrderDO.getTotalCharge());
                    if(chargeReportDO.getProjectId()==null){
                        chargeReportDO.setProjectId(chargeOrderDO.getProjectId());
                    }
                    if(StringUtils.isEmpty(chargeReportDO.getStationId())){
                        chargeReportDO.setStationId(chargeOrderDO.getStationId());
                    }
                }

            }
            chargeReportDO.setTotalNumber(totalNumber);
            chargeReportDO.setTotalPower(totalPower);
            chargeReportDO.setTotalTime(totalTime);
            chargeReportDO.setReportDate(new Date());
            chargeReportDO.setSignCode(id);
            chargeReportDO.setReportDimension(Constant.Dimension.equipment);
            chargeReportDO.setReportType(Constant.ReportType.Day);
            repository.save(chargeReportDO);
        }
    }

}
