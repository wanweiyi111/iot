package com.hzyw.basic.service.impl;

import com.hzyw.basic.dao.mapper.SensorReportDao;
import com.hzyw.basic.datasource.DataSourceKey;
import com.hzyw.basic.datasource.DynamicDataSourceContextHolder;
import com.hzyw.basic.datasource.TargetDataSource;
import com.hzyw.basic.domain.DateDTO;
import com.hzyw.basic.dos.SensorReportDO;
import com.hzyw.basic.service.SensorService;
import com.hzyw.basic.util.Constant;
import com.hzyw.basic.util.DateTools;
import com.hzyw.basic.vo.ResSensorReportVO;
import com.hzyw.basic.vo.ResSensorVO;
import com.hzyw.basic.vo.SearchLightPowerVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class SensorServiceImpl implements SensorService {

    @Resource
    SensorReportDao dao;


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
            default :
        }
        return resSensorVO;
    }

    private ResSensorVO findMonthReport(SearchLightPowerVO searchVO) {
        Date date = CollectionUtils.isEmpty(searchVO.getSearchDate()) ? new Date() : searchVO.getSearchDate().get(0);
        DateDTO monthFullDay = DateTools.getMonthFullDay(date);

        searchVO.setSearchDate(monthFullDay.getDateList());
        DynamicDataSourceContextHolder.set(DataSourceKey.DB_SENSOR);
        List<SensorReportDO> reportList = dao.findWeekReport(searchVO);
        return getResultVO(monthFullDay.getStrList(), reportList);
    }

    private ResSensorVO findWeekReport(SearchLightPowerVO searchVO)
    {
        Date date = CollectionUtils.isEmpty(searchVO.getSearchDate()) ? new Date() : searchVO.getSearchDate().get(0);
        DateDTO workDate = DateTools.getTimeInterval(date);
        searchVO.setSearchDate(workDate.getDateList());
        DynamicDataSourceContextHolder.set(DataSourceKey.DB_SENSOR);
        List<SensorReportDO> reportList = dao.findWeekReport(searchVO);
        return getResultVO(workDate.getStrList(), reportList);
    }


    private ResSensorVO getResultVO(List<String> stringDate,List<SensorReportDO> reportList){
        SimpleDateFormat fs = new SimpleDateFormat("MM/dd");
        ResSensorVO resSensorVO=new ResSensorVO();
        resSensorVO.setDates(stringDate);
        List<ResSensorReportVO> data=new ArrayList<>();
//        Set<String> ProjectIds=new HashSet<>();
//        for (SensorReportDO sensorReportDO:reportList) {
//            ProjectIds.add(sensorReportDO.getSignCode());
//        }
//        for (String str:ProjectIds) {
            ResSensorReportVO resSensorReportVO=new ResSensorReportVO();
//            resSensorReportVO.setSignCode(str);
            List<Float> temperatures=new ArrayList<>();
            List<Float> pm25s=new ArrayList<>();
            List<Float> pm10s=new ArrayList<>();
            List<Float> humiditys=new ArrayList<>();
            List<Float> pressures=new ArrayList<>();
            List<Float> windSpeed=new ArrayList<>();
            List<Float> windDirections=new ArrayList<>();
            List<Float> rainfalls=new ArrayList<>();
            List<Float> noises=new ArrayList<>();

            for (int i = 0; i < stringDate.size(); i++) {
                temperatures.add(0F);
                pm25s.add(0F);
                pm10s.add(0F);
                humiditys.add(0F);
                pressures.add(0F);
                windSpeed.add(0F);
                windDirections.add(0F);
                rainfalls.add(0F);
                noises.add(0F);
            }
            for (SensorReportDO sensorReportDO:reportList){
//                if (str.equalsIgnoreCase(sensorReportDO.getSignCode())){
                Date reportDate = sensorReportDO.getReportDate();
                String date=fs.format(reportDate);
                    int i = stringDate.indexOf(date);
                    temperatures.set(i,sensorReportDO.getTemperature());
                    pm25s.set(i,sensorReportDO.getPm25());
                    pm10s.set(i,sensorReportDO.getPm10());
                    humiditys.set(i,sensorReportDO.getHumidity());
                    pressures.set(i,sensorReportDO.getPressure());
                    windSpeed.set(i,sensorReportDO.getWindSpeed());
                    windDirections.set(i,sensorReportDO.getWindDirection());
                    rainfalls.set(i,sensorReportDO.getRainfall());
                    noises.set(i,sensorReportDO.getNoise());

                    if (StringUtils.isEmpty(sensorReportDO.getEquipmentName())) {
                        sensorReportDO.setEquipmentName(sensorReportDO.getEquipmentName());
                    }

            }
//            }
            for (int i = 0; i < humiditys.size(); i++) {
                // 如果为0或null 给默认值
                if(temperatures.get(i)==null||temperatures.get(i)==0F){
                    temperatures.set(i,20F);
                }
                if(pm25s.get(i)==null||pm25s.get(i)==0F){
                    pm25s.set(i,10F);
                }
                if(pm10s.get(i)==null||pm10s.get(i)==0F){
                    pm10s.set(i,14F);
                }
                if(humiditys.get(i)==null||humiditys.get(i)==0F){
                    humiditys.set(i,14F);
                }
                if(pressures.get(i)==null||pressures.get(i)==0F){
                    pressures.set(i,1000F);
                }
                if(windSpeed.get(i)==null||windSpeed.get(i)==0F){
                    windSpeed.set(i,5.5F);
                }
                if(windDirections.get(i)==null||windDirections.get(i)==0F){
                    windDirections.set(i,222F);
                }
                if(rainfalls.get(i)==null||rainfalls.get(i)==0F){
                    rainfalls.set(i,1500F);
                }
                if(noises.get(i)==null||noises.get(i)==0F){
                    noises.set(i,50F);
                }

            }

            resSensorReportVO.setHumiditys(humiditys);
            resSensorReportVO.setTemperatures(temperatures);
            resSensorReportVO.setPm10s(pm10s);
            resSensorReportVO.setPm25s(pm25s);
            resSensorReportVO.setNoises(noises);
            resSensorReportVO.setRainfall(rainfalls);
            resSensorReportVO.setWindDirections(windDirections);
            resSensorReportVO.setWindSpeed(windSpeed);
            resSensorReportVO.setPressures(pressures);
            data.add(resSensorReportVO);
//        }
        resSensorVO.setData(data);
        return resSensorVO;
    }


    @TargetDataSource(dataSourceKey = DataSourceKey.DB_SENSOR)
    public List<SensorReportDO> findTodayCounts(){
        DynamicDataSourceContextHolder.set(DataSourceKey.DB_SENSOR);
        return dao.findTodayCounts();
    }
}
