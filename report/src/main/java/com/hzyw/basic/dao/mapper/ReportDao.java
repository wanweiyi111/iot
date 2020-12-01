package com.hzyw.basic.dao.mapper;

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
public interface ReportDao {

    List<ReportVO> findByPercentage(SearchVO vo);

    List<LightRuntimeDO> getCountByToday();

    List<ReportVO> findByReportList(List<Date> list);

    List<PoleEquipmentVO> findBindings();

    List<LightRuntimeDO> findAllLight();

    List<LightRuntimeDO> findCountByMonth(List<Date> list);
}
