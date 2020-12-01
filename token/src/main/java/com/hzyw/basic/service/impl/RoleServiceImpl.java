package com.hzyw.basic.service.impl;

import com.hzyw.basic.dao.mapper.RoleMapper;
import com.hzyw.basic.dos.PermissionDO;
import com.hzyw.basic.dos.RoleDO;
import com.hzyw.basic.exception.ErrorCodeAndMsg;
import com.hzyw.basic.exception.SystemException;
import com.hzyw.basic.service.PermissionService;
import com.hzyw.basic.service.RolePermissionService;
import com.hzyw.basic.service.RoleService;
import com.hzyw.basic.util.TokenConstant;
import com.hzyw.basic.util.TreeUtil;
import com.hzyw.basic.vo.PageVO;
import com.hzyw.basic.vo.RoleVO;
import com.hzyw.basic.vo.TreeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 角色接口类
 */

@Service
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleMapper roleMapper;

	@Resource
	private PermissionService permissionService;

	@Resource
	private RolePermissionService rolePermissionService;

	@Override
	public RoleDO findAuRoleInfo(RoleDO roleDO) {
		return roleMapper.findAuRoleInfo(roleDO);
	}

	@Override
	public PageVO<RoleDO> selectByParameter(String roleName, int currentPage, int pageSize){
		int totalCount = roleMapper.findAuRoleTotalCount(roleName);
		int pageNo = (currentPage - 1) * pageSize;
		PageVO<RoleDO> pageResult = new PageVO<>(currentPage, pageSize, totalCount);
		pageResult.setCode(200);
		pageResult.setMessage("SUCCESS");
		List<RoleDO> resultList = roleMapper.selectByParameter(roleName, pageNo, pageSize);
		if (resultList == null || resultList.size() == 0) {
			resultList = new ArrayList<>();
		}
		pageResult.setData(resultList);
		return pageResult;
	}

	@Override
	public int deleteByParameter(String roleIds){
		try{
			String[] ids = roleIds.split(TokenConstant.Common.SPLIT_COMMA);
			List<String> userIdList =  Arrays.asList(ids);
			//删除角色权限关联关系数据
			rolePermissionService.deleteByRoleId(userIdList);
			return roleMapper.deleteByParameter(userIdList);
		}catch (Exception e){
			throw new SystemException(ErrorCodeAndMsg.role_delete_error);
		}
	}

	@Override
	public void insertAuRoleDO(RoleDO roleDO){
		try{
			roleMapper.insertAuRoleDO(roleDO);
		} catch (Exception e) {
			throw new SystemException(ErrorCodeAndMsg.role_add_error);
		}
	}

	@Override
	public int updateByParameter(RoleDO roleDO){
		try{
			return roleMapper.updateByParameter(roleDO);
		} catch (Exception e) {
			throw new SystemException(ErrorCodeAndMsg.role_update_error);
		}
	}

	@Override
	public List<RoleDO> queryAllRoleInfo() {
		return roleMapper.selectAllRole();
	}

	@Override
	public List<RoleDO> selectRoleByUser(String username) {
		return roleMapper.selectRoleByUser(username);
	}

	@Override
	public RoleVO checkRolePermissionById(RoleDO role) {
		try{
			RoleDO roleDO = roleMapper.selectRoleById(role);
			if(null == roleDO){
				return null;
			}
			RoleVO roleVO = new RoleVO();
			BeanUtils.copyProperties(roleDO,roleVO);
			List<PermissionDO> rolePermissionList = permissionService.selectPermissionByRoleId(role.getRoleId());
			//封装权限数据结构
			if (rolePermissionList == null || rolePermissionList.size() == 0) {
				rolePermissionList = new ArrayList<>();
			}
			List<String> ids = new ArrayList<>();
			List<TreeVO<PermissionDO>> treeList = new ArrayList<>();
			buildTrees(treeList, rolePermissionList, ids);
			List<TreeVO<PermissionDO>> menuTree = TreeUtil.build(treeList);
			roleVO.setRolePermissionList(menuTree);
			return roleVO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(ErrorCodeAndMsg.role_permission_error);
		}
	}

	@Override
	public RoleVO selectRoleById(RoleDO role) {
		try{
			RoleDO roleDO = roleMapper.selectRoleById(role);
			if(null == roleDO){
				return null;
			}
			RoleVO roleVO = new RoleVO();
			BeanUtils.copyProperties(roleDO,roleVO);
			List<PermissionDO> rolePermissionList = permissionService.selectPermissionByRoleId(role.getRoleId());
			roleVO.setPermissions(rolePermissionList);
			return roleVO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(ErrorCodeAndMsg.role_permission_error);
		}
	}

	private void buildTrees(List<TreeVO<PermissionDO>> trees, List<PermissionDO> menus, List<String> ids) {
		menus.forEach(menu -> {
			ids.add(menu.getId().toString());
			TreeVO treeVO = new TreeVO();
			BeanUtils.copyProperties(menu,treeVO);
			trees.add(treeVO);
		});
	}
}
