package com.hzyw.basic.dao.mapper;

import com.hzyw.basic.dos.SensorReportDO;
import com.hzyw.basic.vo.SearchLightPowerVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SensorReportDao {

    List<SensorReportDO> findWeekReport(SearchLightPowerVO searchVO);

    List<SensorReportDO> findTodayCounts();
}
