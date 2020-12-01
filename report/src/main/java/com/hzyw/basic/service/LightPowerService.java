package com.hzyw.basic.service;

import com.hzyw.basic.vo.ResPowerVO;
import com.hzyw.basic.vo.SearchLightPowerVO;
import org.springframework.stereotype.Service;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Service
public interface LightPowerService {

    ResPowerVO findByDimensionAndType(SearchLightPowerVO searchVO);
}
