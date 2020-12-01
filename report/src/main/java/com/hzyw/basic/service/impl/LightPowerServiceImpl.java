package com.hzyw.basic.service.impl;

import com.hzyw.basic.dao.mapper.LightPowerReportDao;
import com.hzyw.basic.domain.DateDTO;
import com.hzyw.basic.dos.LightPowerReportDO;
import com.hzyw.basic.service.LightPowerService;
import com.hzyw.basic.util.Constant;
import com.hzyw.basic.util.DateTools;
import com.hzyw.basic.vo.ResPoleVO;
import com.hzyw.basic.vo.ResPowerVO;
import com.hzyw.basic.vo.SearchLightPowerVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Service
@Slf4j
public class LightPowerServiceImpl implements LightPowerService {

    @Resource
    LightPowerReportDao dao;


    @Override
    public ResPowerVO findByDimensionAndType(SearchLightPowerVO searchVO) {

        String type = searchVO.getSearchType();
        ResPowerVO resPowerVO=new ResPowerVO();
        switch (type){
            case Constant.ReportType.Week:
                resPowerVO=findWeekReport(searchVO);
                break;
            case Constant.ReportType.Month:
                resPowerVO=findMonthReport(searchVO);
                break;
            case Constant.ReportType.Year:
                resPowerVO=findYearReport(searchVO);
                break;
            default :
        }
        return resPowerVO;
    }

    private ResPowerVO findYearReport(SearchLightPowerVO searchVO) {
        SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM");
        Map<String, Object> monthOfYear = DateTools.getMonthOfYear(new Date());
        List<String> stringDate = (List) monthOfYear.get("String");
        searchVO.setSearchDate((List) monthOfYear.get("date"));
        List<LightPowerReportDO> reportList = dao.findWeekReport(searchVO);

        ResPowerVO resPowerVO=new ResPowerVO();
        resPowerVO.setDates(stringDate);
        List<ResPoleVO> resPoleVOS=new ArrayList<>();
        Set<String> poleIds=new HashSet<>();
        for (LightPowerReportDO lightPowerReportDO:reportList) {
            poleIds.add(lightPowerReportDO.getSignCode());
        }
        for (String str:poleIds) {
            ResPoleVO resPoleVO=new ResPoleVO();
            resPoleVO.setPoleId(Long.parseLong(str));
            List<Double> energyList=new ArrayList<>();
            List<Double> saveList=new ArrayList<>();
            for (int i = 0; i < stringDate.size(); i++) {
                energyList.add(0D);
            }
            for (int i = 0; i <stringDate.size() ; i++) {
                saveList.add(0D);
            }
            for (LightPowerReportDO lightPowerReportDO:reportList){
                if (str.equalsIgnoreCase(lightPowerReportDO.getSignCode())){
                    String date=fs.format(lightPowerReportDO.getReportDate());
                    int i = stringDate.indexOf(date);
                    energyList.set(i,lightPowerReportDO.getElectricEnergy());
                    saveList.set(i,lightPowerReportDO.getSaveEnergy());
                    if (StringUtils.isEmpty(resPoleVO.getEquipmentName())) {
                        resPoleVO.setEquipmentName(lightPowerReportDO.getEquipmentName());
                    }
                }
            }
            resPoleVO.setEnergyList(energyList);
            resPoleVO.setSaveList(saveList);
            resPoleVOS.add(resPoleVO);
        }
        resPowerVO.setData(resPoleVOS);
        return resPowerVO;
    }

    private ResPowerVO findMonthReport(SearchLightPowerVO searchVO) {
        SimpleDateFormat fs = new SimpleDateFormat("MM/dd");
        DateDTO monthFullDay = DateTools.getMonthFullDay(new Date());
        List<String> stringDate = monthFullDay.getStrList();
        searchVO.setSearchDate(monthFullDay.getDateList());
        List<LightPowerReportDO> reportList = dao.findWeekReport(searchVO);

        ResPowerVO resPowerVO=new ResPowerVO();
        resPowerVO.setDates(stringDate);
        List<ResPoleVO> resPoleVOS=new ArrayList<>();
        Set<String> poleIds=new HashSet<>();
        for (LightPowerReportDO lightPowerReportDO:reportList) {
            poleIds.add(lightPowerReportDO.getSignCode());
        }
        for (String str:poleIds) {
            ResPoleVO resPoleVO=new ResPoleVO();
            resPoleVO.setPoleId(Long.parseLong(str));
            List<Double> energyList=new ArrayList<>();
            List<Double> saveList=new ArrayList<>();
            for (int i = 0; i < stringDate.size(); i++) {
                energyList.add(0D);
            }
            for (int i = 0; i <stringDate.size() ; i++) {
                saveList.add(0D);
            }
            for (LightPowerReportDO lightPowerReportDO:reportList){
                if (str.equalsIgnoreCase(lightPowerReportDO.getSignCode())){
                    String date=fs.format(lightPowerReportDO.getReportDate());
                    int i = stringDate.indexOf(date);
                    energyList.set(i,lightPowerReportDO.getElectricEnergy());
                    saveList.set(i,lightPowerReportDO.getSaveEnergy());
                    if (StringUtils.isEmpty(resPoleVO.getEquipmentName())) {
                        resPoleVO.setEquipmentName(lightPowerReportDO.getEquipmentName());
                    }
                }
            }
            resPoleVO.setEnergyList(energyList);
            resPoleVO.setSaveList(saveList);
            resPoleVOS.add(resPoleVO);
        }
        resPowerVO.setData(resPoleVOS);
        return resPowerVO;
    }

    private ResPowerVO findWeekReport(SearchLightPowerVO searchVO) {
        SimpleDateFormat fs = new SimpleDateFormat("MM/dd");
        DateDTO timeInterval = DateTools.getTimeInterval(new Date());
        List<String> stringDate = timeInterval.getStrList();
        List<Date> dates = timeInterval.getDateList();
        searchVO.setSearchDate(dates);
        List<LightPowerReportDO> reportList = dao.findWeekReport(searchVO);

        ResPowerVO resPowerVO=new ResPowerVO();
        resPowerVO.setDates(stringDate);
        List<ResPoleVO> resPoleVOS=new ArrayList<>();
        Set<String> poleIds=new HashSet<>();
        for (LightPowerReportDO lightPowerReportDO:reportList) {
            poleIds.add(lightPowerReportDO.getSignCode());
        }
        for (String str:poleIds) {
            ResPoleVO resPoleVO=new ResPoleVO();
            resPoleVO.setPoleId(Long.parseLong(str));
            List<Double> energyList=new ArrayList<>();
            List<Double> saveList=new ArrayList<>();
            for (int i = 0; i < stringDate.size(); i++) {
                energyList.add(0D);
                saveList.add(0D);
            }
            for (LightPowerReportDO lightPowerReportDO:reportList){
                if (str.equalsIgnoreCase(lightPowerReportDO.getSignCode())){
                    String date=fs.format(lightPowerReportDO.getReportDate());
                    int i = stringDate.indexOf(date);
                    energyList.set(i,lightPowerReportDO.getElectricEnergy());
                    saveList.set(i,lightPowerReportDO.getSaveEnergy());
                    if (StringUtils.isEmpty(resPoleVO.getEquipmentName())) {
                        resPoleVO.setEquipmentName(lightPowerReportDO.getEquipmentName());
                    }
                }
            }
            resPoleVO.setEnergyList(energyList);
            resPoleVO.setSaveList(saveList);
            resPoleVOS.add(resPoleVO);
        }
        resPowerVO.setData(resPoleVOS); //Y轴显示的值
        return resPowerVO;
    }


}
