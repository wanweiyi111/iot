package com.hzyw.basic.service.impl;

import com.hzyw.basic.dao.mapper.RolePermissionMapper;
import com.hzyw.basic.dos.RolePermissionDO;
import com.hzyw.basic.service.RolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * AuRolePermissionTServiceImpl数据库操作接口类
 */

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

	@Resource
	private RolePermissionMapper rolePermissionMapper;

	@Override
	public int deleteById(String id){
		return rolePermissionMapper.deleteById(id);
	}

	@Override
	public int insertRolePermission(RolePermissionDO rolePermissionDO){
		return rolePermissionMapper.insertRolePermission(rolePermissionDO);
	}

	@Override
	public int updateById(RolePermissionDO rolePermissionDO){
		return rolePermissionMapper.updateById(rolePermissionDO);
	}

	@Override
	public int insertRolePermissionList(Long roleId, List<Integer> permissionList) {
		return rolePermissionMapper.insertRolePermissionList(roleId,permissionList);
	}

	@Override
	public int deleteByRoleId(List<String> roleId) {
		return rolePermissionMapper.deleteByRoleId(roleId);
	}
}
