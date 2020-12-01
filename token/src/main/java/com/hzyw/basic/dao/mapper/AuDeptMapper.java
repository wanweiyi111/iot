package com.hzyw.basic.dao.mapper;


import com.hzyw.basic.vo.DeptVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门Mapper
 */
@Repository
@Mapper
public interface AuDeptMapper {

    //获取所有部门信息
    List<DeptVO> queryAllDeptInfo();
}
