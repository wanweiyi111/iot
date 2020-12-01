package com.hzyw.basic.dao.mapper;

import com.hzyw.basic.dos.LightPowerReportDO;
import com.hzyw.basic.vo.SearchLightPowerVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LightPowerReportDao {

    List<LightPowerReportDO> findWeekReport(SearchLightPowerVO searchVO);

    LightPowerReportDO findEnergySaving(Long id);
}
