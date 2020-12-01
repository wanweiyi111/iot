package com.hzyw.basic.dao.mapper;

import com.hzyw.basic.dos.ChargeOrderDO;
import com.hzyw.basic.dos.ChargeReportDO;
import com.hzyw.basic.vo.SearchLightPowerVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ChargeReportDao {

    List<ChargeReportDO> findWeekReport(SearchLightPowerVO searchVO);

    List<ChargeOrderDO> findTodayCounts();
}
