package com.hzyw.basic.dao.mapper;

import com.hzyw.basic.dos.RolePermissionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限角色关系接口类
 */
@Repository
@Mapper
public interface RolePermissionMapper{

	int CheckPolePermissionByID();


	/**删除（根据主键ID删除）*/
	int deleteById(String id);

	/**
	 * 根据角色ID删除角色权限关系数据
	 */
	int deleteByRoleId(List<String> roleIds);

	/**添加*/
	int insertRolePermission(RolePermissionDO rolePermissionDO);

	/**批量添加角色权限关系数据*/
	int insertRolePermissionList(@Param("roleId") Long roleId, @Param("permissionList") List<Integer> permissionList);

	/**修改*/
	int updateById(RolePermissionDO rolePermissionDO);

	/**
	 * 保存角色与权限关系
	 * @param rolePermissionDO
	 */
	void insertRoleMenu(@Param("rolePermission")RolePermissionDO rolePermissionDO);

	/**
	 * 根据角色Id查询权权限
	 * @param roleId
	 * @return
	 */
    List<RolePermissionDO> selectRolePermissionByRoleId(@Param("roleId") Long roleId);
}
