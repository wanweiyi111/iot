package com.hzyw.basic.service.impl;

import com.hzyw.basic.dao.mapper.LoginLogMapper;
import com.hzyw.basic.dos.LoginLogDO;
import com.hzyw.basic.service.LoginLogService;
import com.hzyw.basic.util.HttpContextUtil;
import com.hzyw.basic.util.IPUtil;
import com.hzyw.basic.vo.PageVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * AuLoginLogTServiceImpl数据库操作接口类
 */

@Service
public class LoginLogServiceImpl implements LoginLogService {

	@Resource
	private LoginLogMapper loginLogMapper;

	@Override
	public PageVO<LoginLogDO> selectByParameter(String userName, int currentPage, int pageSize){
		int totalCount = loginLogMapper.findAuLoginLogTotalCount(userName);
		int pageNo = (currentPage - 1) * pageSize;
		PageVO<LoginLogDO> pageResult = new PageVO<>(currentPage, pageSize, totalCount);
		pageResult.setCode(200);
		pageResult.setMessage("SUCCESS");
		List<LoginLogDO> resultList = loginLogMapper.selectByParameter(userName, pageNo, pageSize);
		if (resultList == null || resultList.size() == 0) {
			resultList = new ArrayList<>();
		}
		pageResult.setData(resultList);
		return pageResult;
	}


	@Override
	public void insertAuLoginLogDO(LoginLogDO loginLog){
		loginLog.setLoginTime(new Date());
		HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
		String ip = IPUtil.getIpAddr(request);
		loginLog.setIp(ip);
		loginLogMapper.insertLoginLogDO(loginLog);
	}


}
