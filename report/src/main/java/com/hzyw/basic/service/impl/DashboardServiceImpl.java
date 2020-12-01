package com.hzyw.basic.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hzyw.basic.dao.jpa.LightPowerReportRepository;
import com.hzyw.basic.dao.mapper.DashBoardDao;
import com.hzyw.basic.dao.mapper.ReportDao;
import com.hzyw.basic.datasource.DataSourceCodeName;
import com.hzyw.basic.datasource.DataSourceKey;
import com.hzyw.basic.datasource.DynamicDataSourceContextHolder;
import com.hzyw.basic.datasource.TargetDataSource;
import com.hzyw.basic.dos.LightPowerReportDO;
import com.hzyw.basic.dos.LightRuntimeDO;
import com.hzyw.basic.service.DashboardService;
import com.hzyw.basic.service.ReportService;
import com.hzyw.basic.util.DateTools;
import com.hzyw.basic.util.DateToolsN;
import com.hzyw.basic.vo.CountListVO;
import com.hzyw.basic.vo.PoleEquipmentVO;
import com.hzyw.basic.vo.ReportVO;
import com.hzyw.basic.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    /*@Resource
    LightPowerReportRepository lightPowerReportRepository;*/
    @Resource
    DashBoardDao dashBoardDao;
    
    @TargetDataSource(dataSourceKey = DataSourceKey.DB_UNIONS)//一键告警
    @Override 
    public List<JSONObject> stateAlarmNumMonthly(JSONObject vo) {
    	//统计一键告警，各个月的一键告警数量
    	//返回结构<"data":[{"1月":10},{"2月":4}.....]>
     	List<JSONObject> data = new ArrayList<JSONObject>();
    	
    	//获取过去十二个月的月份
    	String[] last12Months = DateToolsN.getLast12Months();  
	    for(String m : last12Months){
	    	JSONObject monthly1 = new JSONObject();
	    	monthly1.put(m, 0);
	    	data.add(monthly1);
	    }
	       
    	//返回十二个月的统计
	    List<JSONObject>  stateAlarmNumMonthlyList = dashBoardDao.stateAlarmNumMonthly(vo);
	    for(JSONObject item : stateAlarmNumMonthlyList){//遍历返回列表
	    	String key = item.getString("nYearMonth");
 	    	for(JSONObject m : data){//将SQL返回的统计值覆盖到相应的月份上
 		    	if(m.containsKey(key)){
 		    		m.put(key, item.get("Alarm_Total_CNT"));
 		    	}
 		    }
 	    }
	    
	    return returnFormat(data); 
     }

    @TargetDataSource(dataSourceKey = DataSourceKey.DB_LIGHT)//照明能耗
	@Override
	public List<JSONObject> stateLightEnergyMonthly(JSONObject vo) {
		//实际上照明能耗是没有这种数据的，因为采集源（灯）上报的是时间点的电流电压值，而非经过专业累计的
    	//为满足本次临时方案做法：AVG每个设备的电流电压，电流*电压=功率，统计出每个设备的功率，乘于12小时（开半天）
     	//返回结构<"data":[{"1月":10},{"2月":4}.....]>
     	List<JSONObject> data = new ArrayList<JSONObject>();
    	
    	//获取过去十二个月的月份
    	String[] last12Months = DateToolsN.getLast12Months();  
	    for(String m : last12Months){
	    	JSONObject monthly1 = new JSONObject();
	    	monthly1.put(m, 0);
	    	data.add(monthly1);
	    }
	       
    	//返回十二个月的统计
	    List<JSONObject>  stateAlarmNumMonthlyList = dashBoardDao.stateLightEnergyMonthly(vo);
	    for(JSONObject item : stateAlarmNumMonthlyList){//遍历返回列表
	    	String key = item.getString("nYearMonth");
 	    	for(JSONObject m : data){//将SQL返回的统计值覆盖到相应的月份上
 		    	if(m.containsKey(key)){
 		    		m.put(key, item.get("Total_Light_Energy_Consumption"));
 		    	}
 		    }
 	    }
	    
	    return returnFormat(data);  
	}

    //@TargetDataSource(dataSourceKey = DataSourceKey.DB_WIFI)//照明能耗
	@Override
	public List<JSONObject> stateWifiLogonUserMonthly(JSONObject vo) {
        List<JSONObject> data = new ArrayList<JSONObject>();
        
    	//获取过去十二个月的月份
    	String[] last12Months = DateToolsN.getLast12Months();  
	    for(String m : last12Months){
	    	JSONObject monthly1 = new JSONObject();
	    	monthly1.put(m, 0);
	    	data.add(monthly1);
	    }
	       
    	//返回十二个月的统计
	    List<JSONObject>  stateAlarmNumMonthlyList = dashBoardDao.stateWifiLogonUserMonthly(vo);
	    for(JSONObject item : stateAlarmNumMonthlyList){//遍历返回列表
	    	String key = item.getString("nYearMonth");
 	    	for(JSONObject m : data){//将SQL返回的统计值覆盖到相应的月份上
 		    	if(m.containsKey(key)){
 		    		m.put(key, item.get("WIFI_ACCESS_QTY"));
 		    	}
 		    }
 	    }
	    
     	return returnFormat(data); 
	}
    
    public static List<JSONObject> returnFormat(List<JSONObject> data){
    	List<JSONObject> dataN = new ArrayList<JSONObject>();
	    for(JSONObject item : data){
	    	for(String key : item.keySet()){
	    		JSONObject monthly1 = new JSONObject();
		    	monthly1.put(key.substring(5, 7), item.get(key));
		    	dataN.add(monthly1);
	    	}
	    }
	    return dataN;
    }

    @TargetDataSource(dataSourceKey = DataSourceKey.DB_UNIONS)
	@Override
	public List<JSONObject> stateExceptionNumMonthly(JSONObject vo) {
		List<JSONObject> data = new ArrayList<JSONObject>();
 	    List<JSONObject>  stateExceptionNumMonthlyList = dashBoardDao.stateExceptionNumMonthly(vo);
	    for(JSONObject item : stateExceptionNumMonthlyList){//遍历返回列表
	    	String key = item.getString("equipment_Type_Name");
	    	if(key == null)continue;
	    	JSONObject monthly1 = new JSONObject();
	    	monthly1.put(key, item.get("EXCEPTION_TOTAL_QTY"));
 	    	data.add(monthly1);
 	    }
     	return data;
	}

	/* 
	 * CASE equipment_type WHEN 4112 THEN '照明' WHEN 4144 THEN '大屏' WHEN 4128
			THEN '安防监控' WHEN 4176 THEN '环境' WHEN 4192 THEN '雷达' WHEN 4129 THEN
			'WIFI' WHEN 4208 THEN '智能锁' WHEN 4240 THEN '紧急呼叫' WHEN 4272 THEN
			'公共广播' WHEN 4096 THEN '网关' WHEN 8192 THEN 'PLC集中控制器' WHEN 8193 THEN
			'充电桩' END equipment_Type_Name
	 * (non-Javadoc)
	 * @see com.hzyw.basic.service.DashboardService#stateDeviceNumByType(com.alibaba.fastjson.JSONObject)
	 */
	@Override
	public List<JSONObject> stateDeviceNumByType(JSONObject vo) {
		List<JSONObject> data = new ArrayList<JSONObject>();
    	//返回十二个月的统计
	    JSONObject alldev = new JSONObject();
	    List<JSONObject>  templist = dashBoardDao.countDevNumscreen(vo);

 	    alldev.put("信息发布", templist.get(0)!=null?templist.get(0).get("deviceNum"):0);
 	    data.add(alldev);
 	    
 	    alldev = new JSONObject();
	    templist = dashBoardDao.countDevNumlight(vo);
	    alldev.put("智慧照明", templist.get(0)!=null?templist.get(0).get("deviceNum"):0);
	    data.add(alldev);
	    
	    alldev = new JSONObject();
	    templist = dashBoardDao.countDevNumcamera(vo);
	    alldev.put("安防监控", templist.get(0)!=null?templist.get(0).get("deviceNum"):0);
	    data.add(alldev);
	    
	    /*alldev = new JSONObject();
	    templist = dashBoardDao.countDevNumsensor(vo);
	    alldev.put("环境监测", templist.get(0)!=null?templist.get(0).get("deviceNum"):0);
	    data.add(alldev);*/
	    
	    alldev = new JSONObject();
	    templist = dashBoardDao.countDevNumalarm(vo);
	    alldev.put("应急呼叫", templist.get(0)!=null?templist.get(0).get("deviceNum"):0);
	    data.add(alldev);
	    
	    alldev = new JSONObject();
	    templist = dashBoardDao.countDevNumwifi(vo);
	    alldev.put("智慧WiFi", templist.get(0)!=null?templist.get(0).get("deviceNum"):0);
	    data.add(alldev);
	    
	    alldev = new JSONObject();
	    templist = dashBoardDao.countDevNumunionsLock(vo);
	    alldev.put("智能柜锁", templist.get(0)!=null?templist.get(0).get("deviceNum"):0);
	    data.add(alldev);
	    
	    alldev = new JSONObject();
	    templist = dashBoardDao.countDevNumunionsHorn(vo);
	    alldev.put("公共广播", templist.get(0)!=null?templist.get(0).get("deviceNum"):0);
	    data.add(alldev);
	    
	    alldev = new JSONObject();
	    templist = dashBoardDao.countDevNumradar(vo);
	    alldev.put("智慧雷达", templist.get(0)!=null?templist.get(0).get("deviceNum"):0);
	    data.add(alldev);
     	return data; 
	}
	
	@Override
	public List<JSONObject> statAllDevStatus(JSONObject vo) {
		List<JSONObject> data = new ArrayList<JSONObject>();
 	    List<JSONObject>  statAllDevStatusList = dashBoardDao.statAllDevStatus(vo);
	    for(JSONObject item : statAllDevStatusList){//遍历返回列表
	    	String type = item.getString("equipmentType");//
	    	if(type == null || getEn(Integer.parseInt(type)) == null){
	    		continue;
	    	}
 	    	item.put("name", getEn(Integer.parseInt(type)).getName());
	    	data.add(item);
 	    }
      	return data;
	}
	
	
	@Override
	public List<JSONObject> selectAllDevCodeByProjectId(JSONObject vo) {
		//sql直接指定数据库前缀，这里不需要切换数据源，当前数据源指向哪个库都可以
      	return dashBoardDao.selectAllDevCodeByProjectId(vo);
	}
	
	public static DataSourceCodeName getEn(int type){
        return getByCode(type,DataSourceCodeName.class);
    } 
	
	public static<T extends DataSourceCodeName> T getByCode(Integer code, Class<T> tClass){
        for(T each:tClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }

/*    public void saveLightPowerReport(List<LightPowerReportDO> lightPowerReportDOS){
        lightPowerReportRepository.saveAll(lightPowerReportDOS);
    }*/

}
