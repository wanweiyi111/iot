package com.hzyw.basic.service.impl;

import com.hzyw.basic.dao.mapper.UserRoleMapper;
import com.hzyw.basic.dos.UserRoleDO;
import com.hzyw.basic.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 用户角色关系
 */

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Resource
	private UserRoleMapper auUserRoleTMapper;

	@Override
	public int deleteById(String id){
		return auUserRoleTMapper.deleteById(id);
	}

	@Override
	public int insert(UserRoleDO auUserRoleT){
		return auUserRoleTMapper.insert(auUserRoleT);
	}

	@Override
	public int updateById(UserRoleDO auUserRoleT){
		return auUserRoleTMapper.updateById(auUserRoleT);
	}


}
