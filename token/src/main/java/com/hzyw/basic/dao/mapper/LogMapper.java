package com.hzyw.basic.dao.mapper;

import java.util.List;
import com.hzyw.basic.dos.LogDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 操作日志接口类
 */
@Repository
@Mapper
public interface LogMapper{

	/**查询（根据主键ID查询*/
	List<LogDO>  selectByParameter(@Param("userName") String userName, @Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

	int findAuLogTotalCount(@Param("userName") String userName);

	/**添加*/
	int insertLogDO(@Param("auLog") LogDO auLog);


}
