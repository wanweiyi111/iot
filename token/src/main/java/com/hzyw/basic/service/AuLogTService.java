package com.hzyw.basic.service;

import com.hzyw.basic.dos.LogDO;
import com.hzyw.basic.vo.PageVO;

/**
 * 操作日志接口类
 */
public interface AuLogTService {

	/**查询（根据主键ID查询*/
	PageVO<LogDO> selectByParameter(String userName, int currentPage, int pageSize);

	/**添加*/
	int insertLogDO(LogDO auLog);


}
