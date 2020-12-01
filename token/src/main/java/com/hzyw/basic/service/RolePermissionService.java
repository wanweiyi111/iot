package com.hzyw.basic.service;

import com.hzyw.basic.dos.RolePermissionDO;

import java.util.List;

/**
 * 数据库操作接口类
 */
public interface RolePermissionService {


	/**删除（根据主键ID删除）*/
	int deleteById(String id);

	/**添加*/
	int insertRolePermission(RolePermissionDO rolePermissionDO);

	/**修改*/
	int updateById(RolePermissionDO rolePermissionDO);

	/*批量添加角色权限关系数据*/
	int insertRolePermissionList(Long roleId, List<Integer> permissionList);

	int deleteByRoleId(List<String> roleId);
}
