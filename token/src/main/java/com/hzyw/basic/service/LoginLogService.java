package com.hzyw.basic.service;


import com.hzyw.basic.dos.LoginLogDO;
import com.hzyw.basic.vo.PageVO;

/**
 * 登录日志类
 */
public interface LoginLogService {

	/**查询（根据主键ID查询*/
	PageVO<LoginLogDO> selectByParameter(String userName, int currentPage, int pageSize);

	/**添加*/
	void insertAuLoginLogDO(LoginLogDO LoginLog);

}
