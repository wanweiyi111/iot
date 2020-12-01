package com.hzyw.basic.dao.mapper;


import com.hzyw.basic.dos.LoginLogDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 登录日志类
 */
@Repository
@Mapper
public interface LoginLogMapper {

	/**查询（根据主键ID查询*/
	List<LoginLogDO> selectByParameter(@Param("userName") String userName, @Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

	int findAuLoginLogTotalCount(@Param("userName") String userName);

	/**添加*/
	int insertLoginLogDO(@Param("auLoginLog") LoginLogDO LoginLog);



}
