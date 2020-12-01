package com.hzyw.basic.service;

import com.hzyw.basic.dos.LightRuntimeDO;
import com.hzyw.basic.vo.CountListVO;
import com.hzyw.basic.vo.ReportVO;
import com.hzyw.basic.vo.SearchVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Service
public interface ReportService {


    List<ReportVO> findByPercentage(SearchVO vo);

    CountListVO findByReportList(SearchVO searchVO);

    CountListVO findByReportListMonth(SearchVO searchVO);

    List<LightRuntimeDO> findCountByMonth();

    CountListVO findMonthOfYear(SearchVO searchVO);
}
