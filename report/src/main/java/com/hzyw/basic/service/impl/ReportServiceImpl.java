package com.hzyw.basic.service.impl;

import com.hzyw.basic.dao.jpa.LightPowerReportRepository;
import com.hzyw.basic.dao.mapper.ReportDao;
import com.hzyw.basic.datasource.DataSourceKey;
import com.hzyw.basic.datasource.TargetDataSource;
import com.hzyw.basic.domain.DateDTO;
import com.hzyw.basic.dos.LightPowerReportDO;
import com.hzyw.basic.dos.LightRuntimeDO;
import com.hzyw.basic.service.ReportService;
import com.hzyw.basic.util.DateTools;
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
public class ReportServiceImpl implements ReportService {

    @Resource
    ReportDao dao;

    @Resource
    LightPowerReportRepository lightPowerReportRepository;

    @TargetDataSource(dataSourceKey = DataSourceKey.DB_REPORT)
    @Override
    public List<ReportVO> findByPercentage(SearchVO vo) {
        if (vo.getSearchType() == 1) {
            DateDTO timeInterval = DateTools.getTimeInterval(new Date());
            vo.setDates(timeInterval.getDateList());
        } else if (vo.getSearchType() == 2) {
            DateDTO monthFullDay = DateTools.getMonthFullDay(new Date());
            vo.setDates(monthFullDay.getDateList());
        } else {
            Map<String, Object> monthOfYear = DateTools.getMonthOfYear(new Date());
            vo.setDates((List)monthOfYear.get("date"));
        }
        return dao.findByPercentage(vo);
    }


    @Override
    public CountListVO findByReportList(SearchVO searchVO) {

        List<String> brightnessRate = new ArrayList();
        List<String> energySaving = new ArrayList();
        List<String> errorRate = new ArrayList();
        List<String> electricEnergy = new ArrayList();

        DateDTO timeInterval = DateTools.getTimeInterval(new Date());
        List<String> brightnessDate = timeInterval.getStrList();

        ReportVO reportVO = new ReportVO();
        reportVO.setFailureRate(0.0);
        reportVO.setEnergySaving(0.0);
        reportVO.setLightingRate(0.0);
        CountListVO countListVO = new CountListVO();
        countListVO.setBrightnessDate(brightnessDate);
        countListVO.setErrorDate(brightnessDate);
        countListVO.setEnergySavingDate(brightnessDate);
        countListVO.setElectricDate(brightnessDate);
        List<ReportVO> reportList = dao.findByReportList(timeInterval.getDateList());
        //如果为空则给0
        if (CollectionUtils.isEmpty(reportList)) {
            for (int i = 0; i < 7; i++) {
                brightnessRate.add(reportVO.getLightingRate() + "");
                energySaving.add(reportVO.getEnergySaving() + "");
                errorRate.add(reportVO.getFailureRate() + "");
                electricEnergy.add(reportVO.getLightingRate() +
                        reportVO.getEnergySaving() + "");
            }
            countListVO.setEnergySaving(energySaving);
            countListVO.setBrightnessRate(brightnessRate);
            countListVO.setElectricEnergy(electricEnergy);
            countListVO.setErrorRate(errorRate);
            return countListVO;
        }
        //赋值
        for (ReportVO reportVO1 : reportList) {
            brightnessRate.add(reportVO1.getLightingRate() + "");
            energySaving.add(reportVO1.getEnergySaving() + "");
            errorRate.add(reportVO1.getFailureRate() + "");
            electricEnergy.add(reportVO1.getLightingRate() +
                    reportVO1.getEnergySaving() + "");
        }
        //如果不够7条
        if (reportList.size() < 7) {
            for (int i = 0; i < 7 - reportList.size(); i++) {
                brightnessRate.add(reportVO.getLightingRate() + "");
                energySaving.add(reportVO.getEnergySaving() + "");
                errorRate.add(reportVO.getFailureRate() + "");
                electricEnergy.add(reportVO.getLightingRate() +
                        reportVO.getEnergySaving() + "");
            }
        }
        countListVO.setEnergySaving(energySaving);
        countListVO.setBrightnessRate(brightnessRate);
        countListVO.setElectricEnergy(electricEnergy);
        countListVO.setErrorRate(errorRate);
//        System.out.println(brightnessDate.toString());
        return countListVO;
    }

    @Override
    public CountListVO findByReportListMonth(SearchVO searchVO) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> brightnessRate = new ArrayList();
        List<String> brightnessDate;
//        List<String> energySaving = new ArrayList();
        List<String> errorRate = new ArrayList();
        List<String> electricEnergy = new ArrayList();

        DateDTO monthFullDay = DateTools.getMonthFullDay(new Date(System.currentTimeMillis()));
        brightnessDate = monthFullDay.getStrList();

        CountListVO countListVO = new CountListVO();
        ReportVO reportVO = new ReportVO();
        reportVO.setFailureRate(0.0);
        reportVO.setEnergySaving(0.0);
        reportVO.setLightingRate(0.0);
        countListVO.setBrightnessDate(brightnessDate);
        countListVO.setErrorDate(brightnessDate);
        countListVO.setEnergySavingDate(brightnessDate);
        countListVO.setElectricDate(brightnessDate);
        List<Date> dateList = monthFullDay.getDateList();
        List<ReportVO> reportList = dao.findByReportList(dateList);
        //如果为空则给0
        if (CollectionUtils.isEmpty(reportList)) {
            for (int i = 0; i < brightnessDate.size(); i++) {
                brightnessRate.add(reportVO.getLightingRate() + "");
                errorRate.add(reportVO.getFailureRate() + "");
                electricEnergy.add(reportVO.getLightingRate() +
                        reportVO.getEnergySaving() + "");
            }
            countListVO.setBrightnessRate(brightnessRate);
            countListVO.setElectricEnergy(electricEnergy);
            countListVO.setErrorRate(errorRate);
            return countListVO;
        }
        //如果每天都有数据
        List<Date> days = new ArrayList();
        if (reportList.size() == dateList.size()) {
            for (ReportVO reportVO1 : reportList) {
                brightnessRate.add(reportVO1.getLightingRate() + "");
                errorRate.add(reportVO1.getFailureRate() + "");
                electricEnergy.add(reportVO1.getLightingRate() +
                        reportVO1.getEnergySaving() + "");
            }
        } else {
            for (Date date : dateList) {
                int count = 0;
                for (ReportVO reportVO1 : reportList) {
                    if ((sdf.format(date)).equalsIgnoreCase(sdf.format(reportVO1.getReportDate()))) {
                        brightnessRate.add(reportVO1.getLightingRate() + "");
                        errorRate.add(reportVO1.getFailureRate() + "");
                        electricEnergy.add(reportVO1.getLightingRate() +
                                reportVO1.getEnergySaving() + "");
                        count++;
                    }
                }
                if (count == 0) {
                    brightnessRate.add(reportVO.getLightingRate() + "");
                    errorRate.add(reportVO.getFailureRate() + "");
                    electricEnergy.add(reportVO.getLightingRate() +
                            reportVO.getEnergySaving() + "");
                }

            }


        }
        countListVO.setBrightnessRate(brightnessRate);
        countListVO.setElectricEnergy(electricEnergy);
        countListVO.setErrorRate(errorRate);
//        System.out.println(brightnessDate.toString());
        return countListVO;
    }

    @TargetDataSource(dataSourceKey = DataSourceKey.DB_LIGHT)
    @Override
    public List<LightRuntimeDO> findCountByMonth() {
        DateDTO monthFullDay = DateTools.getMonthFullDay(new Date(System.currentTimeMillis()));
        List<Date> dates = monthFullDay.getDateList();
        return dao.findCountByMonth(dates);
    }

    @Override
    public CountListVO findMonthOfYear(SearchVO searchVO) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        List<String> brightnessRate = new ArrayList();
        List<String> brightnessDate;
        List<String> errorRate = new ArrayList();
        List<String> electricEnergy = new ArrayList();

        Map<String, Object> map = DateTools.getMonthOfYear(new Date());
        brightnessDate = (List) map.get("String");

        CountListVO countListVO = new CountListVO();
        ReportVO reportVO = new ReportVO();
        reportVO.setFailureRate(0.0);
        reportVO.setEnergySaving(0.0);
        reportVO.setLightingRate(0.0);
        countListVO.setBrightnessDate(brightnessDate);
        countListVO.setErrorDate(brightnessDate);
        countListVO.setEnergySavingDate(brightnessDate);
        countListVO.setElectricDate(brightnessDate);
        List<ReportVO> reportList = findByPercentage(searchVO);
        //如果为空则给0
        if (CollectionUtils.isEmpty(reportList)) {
            for (int i = 0; i < brightnessDate.size(); i++) {
                brightnessRate.add(reportVO.getLightingRate() + "");
                errorRate.add(reportVO.getFailureRate() + "");
                electricEnergy.add(reportVO.getLightingRate() +
                        reportVO.getEnergySaving() + "");
            }
            countListVO.setBrightnessRate(brightnessRate);
            countListVO.setElectricEnergy(electricEnergy);
            countListVO.setErrorRate(errorRate);
            return countListVO;
        }
        //如果每天都有数据
        List<Date> days = new ArrayList();
        if (reportList.size() == brightnessDate.size()) {
            for (ReportVO reportVO1 : reportList) {
                brightnessRate.add(reportVO1.getLightingRate() + "");
                errorRate.add(reportVO1.getFailureRate() + "");
                electricEnergy.add(reportVO1.getLightingRate() +
                        reportVO1.getEnergySaving() + "");
            }
        } else {
            for (String date : brightnessDate) {
                int count = 0;
                for (ReportVO reportVO1 : reportList) {
                    Date reportDate = reportVO1.getReportDate();
                    if (date.equalsIgnoreCase(sdf.format(reportDate))) {
                        brightnessRate.add(reportVO1.getLightingRate() + "");
                        errorRate.add(reportVO1.getFailureRate() + "");
                        electricEnergy.add(reportVO1.getLightingRate() +
                                reportVO1.getEnergySaving() + "");
                        count++;
                    }
                }
                if (count == 0) {
                    brightnessRate.add(reportVO.getLightingRate() + "");
                    errorRate.add(reportVO.getFailureRate() + "");
                    electricEnergy.add(reportVO.getLightingRate() +
                            reportVO.getEnergySaving() + "");
                }

            }


        }
        countListVO.setBrightnessRate(brightnessRate);
        countListVO.setElectricEnergy(electricEnergy);
        countListVO.setErrorRate(errorRate);
        return countListVO;
    }


    @TargetDataSource(dataSourceKey = DataSourceKey.DB_LIGHT)
    public List<LightRuntimeDO> findTodayAllCount() {
        return dao.getCountByToday();
    }

    @TargetDataSource(dataSourceKey = DataSourceKey.DB_POLE)
    public List<PoleEquipmentVO> findBindings(){
        return dao.findBindings();
    }


    @TargetDataSource(dataSourceKey = DataSourceKey.DB_LIGHT)
    public List<LightRuntimeDO> findAllLight() {
        return dao.findAllLight();
    }

    public void saveLightPowerReport(List<LightPowerReportDO> lightPowerReportDOS){
        lightPowerReportRepository.saveAll(lightPowerReportDOS);
    }

}
