package com.hzyw.basic.service;

import com.alibaba.fastjson.JSONObject;
import com.hzyw.basic.dos.LightRuntimeDO;
import com.hzyw.basic.vo.CountListVO;
import com.hzyw.basic.vo.ReportVO;
import com.hzyw.basic.vo.SearchVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * by zhu
 */
@Service
public interface DashboardService {

	/**
	 * 统计每月一键告警数
     * 按月统计，统计当前月及历史各月的一键告警数，返回一年的统计
	 * @param vo  projectId
	 * @return
	 */
	List<JSONObject> stateAlarmNumMonthly(JSONObject vo);
	
	/**
	 * 照明能耗
     * 按月统计照明能耗，统计当前月及历史各月的照明能耗，返回一年的统计
	 * @param vo  projectId
	 * @return
	 */
	List<JSONObject> stateLightEnergyMonthly(JSONObject vo);
	
	/**
	 * wifi访问量统计
     * 按月统计，统计当前月及历史各月的wifi登陆用户，返回一年的统计
	 * @param vo  projectId
	 * @return
	 */
	List<JSONObject> stateWifiLogonUserMonthly(JSONObject vo);
	
	
	/**
	 * 告警统计
     * 统计各种设备类型下的告警数,返回当年的统计
	 * @param vo  projectId
	 * @return
	 */
	List<JSONObject> stateExceptionNumMonthly(JSONObject vo);
	
	List<JSONObject> stateDeviceNumByType(JSONObject vo);
	
	/**
	 * 在线率统计
	 * @param vo  projectId
	 * @return
	 */
	List<JSONObject> statAllDevStatus(JSONObject vo);
	
	
	/**
	 * 获取当前项目下的所有设备编号
	 * @param vo
	 * @return
	 */
	List<JSONObject> selectAllDevCodeByProjectId(JSONObject vo);
}
