package com.hzyw.basic.service;


import com.hzyw.basic.dos.UserRoleDO;

/**
 * 用户角色关系
 */
public interface UserRoleService {


	/**删除（根据主键ID删除）*/
	int deleteById(String id);

	/**添加*/
	int insert(UserRoleDO auUserRoleT);

	/**修改*/
	int updateById(UserRoleDO auUserRoleT);

}
