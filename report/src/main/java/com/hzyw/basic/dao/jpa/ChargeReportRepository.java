package com.hzyw.basic.dao.jpa;

import com.hzyw.basic.dos.ChargeReportDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 大屏基础数据接口
 *
 * @author hao yuan
 * @date 2019.08.07
 */
@Repository
@Mapper
public interface ChargeReportRepository extends JpaRepository<ChargeReportDO, Long> {

}
