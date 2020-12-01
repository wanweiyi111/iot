package com.hzyw.basic.dao.mapper;

import com.alibaba.fastjson.JSONObject;
import com.hzyw.basic.dos.LightRuntimeDO;
import com.hzyw.basic.vo.PoleEquipmentVO;
import com.hzyw.basic.vo.ReportVO;
import com.hzyw.basic.vo.SearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface DashBoardDao {

    /**
     * 统计一键告警数,最近12个月的
     */
    List<JSONObject> stateAlarmNumMonthly(JSONObject para);
    
    /**
     * 照明能耗按月统计照明能耗，统计当前月及历史各月的照明能耗，返回一年的统计
     */
    List<JSONObject> stateLightEnergyMonthly(JSONObject para);
    
    /**
     * 告警统计按月统计，统计各个月的告警次数，返回最近12个月的统计
     */
    List<JSONObject> stateWifiLogonUserMonthly(JSONObject para);
    
    /**
     * 统计各种设备类型下的告警数,返回当年的统计
     */
    List<JSONObject> stateExceptionNumMonthly(JSONObject para);
    
    /**
     * 统计各种设备类型的设备总数
     */
    List<JSONObject> countDevNumscreen(JSONObject para);
    List<JSONObject> countDevNumlight(JSONObject para);
    List<JSONObject> countDevNumcamera(JSONObject para);
    List<JSONObject> countDevNumsensor(JSONObject para);
    List<JSONObject> countDevNumalarm(JSONObject para);
    List<JSONObject> countDevNumwifi(JSONObject para);
    List<JSONObject> countDevNumunionsLock(JSONObject para);
    List<JSONObject> countDevNumunionsHorn(JSONObject para);
    List<JSONObject> countDevNumradar(JSONObject para);
    
    /**
     * 统计在线率
     * @param para
     * @return
     */
    List<JSONObject> statAllDevStatus(JSONObject para);
    
    List<JSONObject> selectAllDevCodeByProjectId(JSONObject para);
}
