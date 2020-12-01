package com.hzyw.basic.dao.mapper;

import java.util.List;

import com.hzyw.basic.dos.RoleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 角色接口类
 */
@Repository
@Mapper
public interface RoleMapper{

	/**查询（根据主键ID查询*/
	List<RoleDO> selectByParameter(@Param("roleName") String roleName, @Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

	int findAuRoleTotalCount(@Param("roleName") String roleName);

	RoleDO findAuRoleInfo(@Param("auRole") RoleDO auRole);

	/**删除（根据主键ID删除）*/
	int deleteByParameter(List<String> list);

	/**添加*/
	int insertAuRoleDO(@Param("auRole") RoleDO auRole);

	/**修改*/
	int updateByParameter(@Param("auRole") RoleDO auRole);

	/**查询所有角色*/
	List<RoleDO> selectAllRole();

	//根据用户查询角色
	List<RoleDO> selectRoleByUser(@Param("username") String username);

	/**
	 * 根据角色ID查询角色
	 * @param role
	 * @return
	 */
    RoleDO selectRoleById(RoleDO role);
}
