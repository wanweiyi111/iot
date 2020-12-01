package com.hzyw.basic.dao.mapper;


import com.hzyw.basic.dos.UserRoleDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户角色关系
 */
@Repository
@Mapper
public interface UserRoleMapper{


	/**删除（根据主键ID删除）*/
	int deleteById(String id);

	/**添加*/
	int insert(UserRoleDO auUserRoleT);

	/**修改*/
	int updateById(UserRoleDO auUserRoleT);

}
