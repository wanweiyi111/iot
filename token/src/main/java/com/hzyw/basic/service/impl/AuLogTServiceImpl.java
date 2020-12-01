package com.hzyw.basic.service.impl;

import com.hzyw.basic.dao.mapper.LogMapper;
import com.hzyw.basic.dos.LogDO;
import com.hzyw.basic.service.AuLogTService;
import com.hzyw.basic.vo.PageVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 操作日志接口类
 */

@Service
public class AuLogTServiceImpl implements AuLogTService {

	@Resource
	private LogMapper auLogTMapper;

	@Override
	public PageVO<LogDO> selectByParameter(String userName, int currentPage, int pageSize){
		int totalCount = auLogTMapper.findAuLogTotalCount(userName);
		int pageNo = (currentPage - 1) * pageSize;
		PageVO<LogDO> pageResult = new PageVO<>(currentPage, pageSize, totalCount);
		pageResult.setCode(200);
		pageResult.setMessage("SUCCESS");
		List<LogDO> resultList = auLogTMapper.selectByParameter(userName, pageNo, pageSize);
		if (resultList == null || resultList.size() == 0) {
			resultList = new ArrayList<>();
		}
		pageResult.setData(resultList);
		return pageResult;
	}


	@Override
	public int insertLogDO(LogDO logDO){
		return auLogTMapper.insertLogDO(logDO);
	}



}
