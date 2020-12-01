package com.hzyw.basic.service;

import com.hzyw.basic.dos.RoleDO;
import com.hzyw.basic.vo.PageVO;
import com.hzyw.basic.vo.RoleVO;

import java.util.List;

/**
 * 角色接口类
 */
public interface RoleService {

	/**根据角色名称查询角色*/
	PageVO<RoleDO> selectByParameter(String roleName, int currentPage, int pageSize);

	/*查询角色信息*/
	RoleDO findAuRoleInfo(RoleDO roleDO);

	/**删除（根据主键ID删除）*/
	int deleteByParameter(String roleIds);

	/**添加*/
	void insertAuRoleDO(RoleDO role);

	/**修改*/
	int updateByParameter(RoleDO role);

	List<RoleDO> queryAllRoleInfo();

	/**
	 * 根据用户查询角色
	 * @param username
	 * @return
	 */
    List<RoleDO> selectRoleByUser(String username);

	/**
	 * 根据角色ID查看角色权限信息
	 * @param role
	 * @return
	 */
	RoleVO checkRolePermissionById(RoleDO role);

	/**
	 * 根据角色ID获取角色权限信息
	 * @param role
	 * @return
	 */
	RoleVO selectRoleById(RoleDO role);
}
