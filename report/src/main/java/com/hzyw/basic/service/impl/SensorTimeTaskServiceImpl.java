package com.hzyw.basic.service.impl;

import com.hzyw.basic.dao.jpa.SensorReportRepository;
import com.hzyw.basic.dos.SensorReportDO;
import com.hzyw.basic.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Slf4j
@Service
public class SensorTimeTaskServiceImpl {

    @Resource
    SensorServiceImpl service;
    @Resource
    SensorReportRepository repository;


    @Scheduled(cron = "59 59 23 * * ?")
    public void getCountByDay() {
       //查询数据
       List<SensorReportDO> list=service.findTodayCounts();
       if(!CollectionUtils.isEmpty(list)){
           for (SensorReportDO sensorReportDO:list) {
               sensorReportDO.setEquipmentName(sensorReportDO.getSignCode()+"项目");
               sensorReportDO.setReportDimension(Constant.ReportDimension.project);
               sensorReportDO.setReportDate(new Date());
               sensorReportDO.setReportType(Constant.ReportType.Day);
           }

           repository.saveAll(list);
       }

    }

}
