package com.hzyw.basic.service;


import com.hzyw.basic.dos.PermissionDO;
import com.hzyw.basic.dos.ProjectDeviceDO;
import com.hzyw.basic.vo.PageVO;
import com.hzyw.basic.vo.ResponseVO;

import java.util.List;
import java.util.Map;

/**
 * 权限接口类
 */
public interface PermissionService {

	/**根据权限名称查询权限*/
	PageVO<PermissionDO> selectByParameter(PermissionDO permissionDO, int currentPage, int pageSize);

	/**查询菜单树形列表*/
	Map<String, Object> selectMenuTreeList();

	/**查询角色菜单树形列表*/
	Map<String, Object> selectPoleMenuTreeList();

	/**根据用户查询菜单树状列表*/
	Map<String,Object> selectUserMenuTreeList(String userName,String type);

	/**查询菜单列表*/
	List<PermissionDO> selectMenuList();

	/**删除（根据主键ID删除）*/
	int deleteByParameter(String ids);

	/**添加*/
	ResponseVO insertAuPermissionDO(PermissionDO permission);

	/**修改*/
	ResponseVO updateByParameter(PermissionDO permission);

	/**
	 * 根据用户查询权限集合
	 * @param username
	 * @return
	 */
    List<PermissionDO> selectPermissionByUser(String username,String type);

	/***
	 * 根据角色ID差权限集合
	 */
	List<PermissionDO> selectPermissionByRoleId(Long roleId);

	/**
	 * 新增配置用户查看项目设备数据权限
	 * @param projectDeviceList
	 * @return
	 */
	ResponseVO addProjectDevice(List<ProjectDeviceDO> projectDeviceList);


	/**
	 * 用户名获取用户项目下配置的设备数据权限
	 * @param userName 用户名
	 * @return
	 */
	List<ProjectDeviceDO> getProjectDeviceByUserName(String userName,String projectId);
}
