package com.hzyw.basic.service.impl;

import com.hzyw.basic.dao.mapper.AuDeptMapper;
import com.hzyw.basic.service.AuDeptService;
import com.hzyw.basic.vo.DeptVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AuDeptServiceImpl implements AuDeptService {

    @Resource
    private AuDeptMapper auDeptMapper;

    @Override
    public List<DeptVO> queryAllDeptInfo() {
        return auDeptMapper.queryAllDeptInfo();
    }
}
