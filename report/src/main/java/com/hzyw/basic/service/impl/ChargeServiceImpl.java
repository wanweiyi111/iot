package com.hzyw.basic.service.impl;

import com.hzyw.basic.dao.mapper.ChargeReportDao;
import com.hzyw.basic.datasource.DataSourceKey;
import com.hzyw.basic.datasource.TargetDataSource;
import com.hzyw.basic.domain.DateDTO;
import com.hzyw.basic.dos.ChargeOrderDO;
import com.hzyw.basic.dos.ChargeReportDO;
import com.hzyw.basic.service.ChargeService;
import com.hzyw.basic.util.Constant;
import com.hzyw.basic.util.DateTools;
import com.hzyw.basic.vo.ResChargeReportVO;
import com.hzyw.basic.vo.ResSensorVO;
import com.hzyw.basic.vo.SearchLightPowerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Service
@Slf4j
public class ChargeServiceImpl  implements ChargeService {

    @Resource
    ChargeReportDao dao;



    @TargetDataSource(dataSourceKey = DataSourceKey.DB_CHARGER)
    public List<ChargeOrderDO> findTodayCounts()
    {
        return dao.findTodayCounts();
    }

    @Override
    public ResSensorVO findByTimeDimension(SearchLightPowerVO searchVO) {
        String type = searchVO.getSearchType();
        ResSensorVO resSensorVO=new ResSensorVO();
        switch (type){
            case Constant.ReportType.Week:
                resSensorVO=findWeekReport(searchVO);
                break;
            case Constant.ReportType.Month:
                resSensorVO=findMonthReport(searchVO);
                break;
//            case Constant.ReportType.Year:
//                lightPowerReportDOS=findYearReport(searchVO);
            default :
        }
        return resSensorVO;
    }

    private ResSensorVO findMonthReport(SearchLightPowerVO searchVO) {
        Date date = CollectionUtils.isEmpty(searchVO.getSearchDate()) ? new Date() : searchVO.getSearchDate().get(0);
        DateDTO monthFullDay = DateTools.getMonthFullDay(date);

        List<String> stringDate = monthFullDay.getStrList();
        List<Date> dates = monthFullDay.getDateList();
        searchVO.setSearchDate(dates);
        List<ChargeReportDO> reportList = dao.findWeekReport(searchVO);
        return getResultVO(stringDate,reportList);
    }

    private ResSensorVO findWeekReport(SearchLightPowerVO searchVO)
    {
        Date date = CollectionUtils.isEmpty(searchVO.getSearchDate()) ? new Date() : searchVO.getSearchDate().get(0);
        DateDTO timeInterval = DateTools.getTimeInterval(date);

        List<String> stringDate = timeInterval.getStrList();
        List<Date> dates = timeInterval.getDateList();
        searchVO.setSearchDate(dates);
        List<ChargeReportDO> reportList = dao.findWeekReport(searchVO);
        return getResultVO(stringDate,reportList);
    }


    private ResSensorVO getResultVO(List<String> stringDate,List<ChargeReportDO> reportList){
        SimpleDateFormat fs = new SimpleDateFormat("MM/dd");
        ResSensorVO resSensorVO=new ResSensorVO();
        resSensorVO.setDates(stringDate);
        List<ResChargeReportVO> dataCharge=new ArrayList<>();
        Set<String> ProjectIds=new HashSet<>();
        for (ChargeReportDO chargeReportDO:reportList) {
            ProjectIds.add(chargeReportDO.getProjectId()+"");
        }
        for (String str:ProjectIds) {
            ResChargeReportVO resChargeReportVO=new ResChargeReportVO();
            resChargeReportVO.setProjectId(str);
            List<Integer> totalNumbers=new ArrayList<>();
            List<Double> totalTimes=new ArrayList<>();
            List<Double> totalPowers=new ArrayList<>();

            for (int i = 0; i < stringDate.size(); i++) {
                totalNumbers.add(0);
                totalTimes.add(0D);
                totalPowers.add(0D);

            }
            for (ChargeReportDO chargeReportDO:reportList){
                if (str.equalsIgnoreCase(chargeReportDO.getProjectId()+"")){
                    String date=fs.format(chargeReportDO.getReportDate());
                    int i = stringDate.indexOf(date);
                    totalNumbers.set(i,chargeReportDO.getTotalNumber());
                    totalTimes.set(i,chargeReportDO.getTotalTime()/3600000.0);
                    totalPowers.set(i,chargeReportDO.getTotalPower());
                }
            }
            resChargeReportVO.setTotalNumbers(totalNumbers);
            resChargeReportVO.setTotalTimes(totalTimes);
            resChargeReportVO.setTotalPowers(totalPowers);
            dataCharge.add(resChargeReportVO);
        }
        resSensorVO.setDataCharge(dataCharge);
        return resSensorVO;
    }

}
