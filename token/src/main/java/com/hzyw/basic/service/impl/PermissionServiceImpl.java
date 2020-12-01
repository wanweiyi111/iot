package com.hzyw.basic.service.impl;

import com.hzyw.basic.dao.mapper.PermissionMapper;
import com.hzyw.basic.dao.mapper.RolePermissionMapper;
import com.hzyw.basic.dos.PermissionDO;
import com.hzyw.basic.dos.ProjectDeviceDO;
import com.hzyw.basic.dos.TreeDO;
import com.hzyw.basic.service.PermissionService;
import com.hzyw.basic.util.TokenConstant;
import com.hzyw.basic.util.TreeUtil;
import com.hzyw.basic.vo.PageVO;
import com.hzyw.basic.vo.PermissionVO;
import com.hzyw.basic.vo.ResponseVO;
import com.hzyw.basic.vo.TreeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;


/**
 * 权限接口类
 */

@Service
public class PermissionServiceImpl implements PermissionService {

	@Resource
	private PermissionMapper permissionMapper;
	@Resource
	RolePermissionMapper rolePermissionMapper;

	@Override
	public PageVO<PermissionDO> selectByParameter(PermissionDO permissionDO, int currentPage, int pageSize){
		int totalCount = permissionMapper.selectByParameterCount(permissionDO);
		int pageNo = (currentPage - 1) * pageSize;
		PageVO<PermissionDO> pageResult = new PageVO<>(currentPage, pageSize, totalCount);
		pageResult.setCode(200);
		pageResult.setMessage("SUCCESS");
		List<PermissionDO> resultList = permissionMapper.selectByParameter(permissionDO, pageNo, pageSize);
		if (resultList == null || resultList.size() == 0) {
			resultList = new ArrayList<>();
		}
		pageResult.setData(resultList);
		return pageResult;
	}

	@Override
	public Map<String, Object> selectMenuTreeList() {
		Map<String, Object> result = new HashMap<>();
		PermissionDO auPermissionDO = new PermissionDO();
		auPermissionDO.setType("02");	//查询菜单和子页面类型数据
		List<PermissionDO> menus = permissionMapper.selectMenuList(auPermissionDO);
		if (menus == null || menus.size() == 0) {
			menus = new ArrayList<>();
		}
		List<TreeDO<PermissionDO>> trees = new ArrayList<>();
		List<String> ids = new ArrayList<>();

		List<TreeVO<PermissionDO>> treeList = new ArrayList<>();
		buildTrees(treeList, menus, ids);
		result.put("ids", ids);
		List<TreeVO<PermissionDO>> menuTree = TreeUtil.build(treeList);
		result.put("rows", menuTree);
		result.put("total", menus.size());
		return result;
	}

	@Override
	public List<PermissionDO> selectMenuList() {
		Map<String, Object> result = new HashMap<>();
		PermissionDO auPermissionDO = new PermissionDO();
		auPermissionDO.setType("0");
		return permissionMapper.selectMenuList(auPermissionDO);
	}

	@Override
	public int deleteByParameter(String promissionIds){
		String[] ids = promissionIds.split(TokenConstant.Common.SPLIT_COMMA);
		List<String> promissionList =  new ArrayList<>(Arrays.asList(ids));
		//封装好该权限下的子项目,统一删除
		List<String> list =  new ArrayList<>();
		for (String id:promissionList){
			//递归的将一级分类下的id也加入到集合中
			this.getIds(list,id);
		}
		if (!CollectionUtils.isEmpty(list)){
			promissionList.addAll(list);
		}
		return permissionMapper.deleteByParameter(promissionList);
	}

	@Override
	public ResponseVO insertAuPermissionDO(PermissionDO permissionDO){
		ResponseVO responseVO=new ResponseVO();
		//判断该权限名是否已使用
		PermissionDO permission=permissionMapper.checkPermission(permissionDO);
		if (permission!=null){
			responseVO.failure("该名称已被使用!");
			return responseVO;
		}
		//菜单有排序0:菜单 1:按钮
		if ("0".equals(permissionDO.getType())){
			//判断该排序序号是否被使用
			int count=permissionMapper.checkPermissionOrderNum(permissionDO);
			if (count>0){
				responseVO.failure("该排序已在上级页面中使用!");
				return responseVO;
			}
		}

		//新增
		permissionMapper.insertPermission(permissionDO);
		//判断是否新增的是菜单,是则默认新增一个查看按钮控制菜单权限
		if ("0".equals(permissionDO.getType())){
			PermissionDO checkPermission=buildPermissionDO(permissionDO);
			permissionMapper.insertPermission(checkPermission);
		}
		responseVO.success("新增成功!");
		//保存菜单和角色关系 TODO
		//RolePermissionDO rolePermissionDO = new RolePermissionDO();
		//rolePermissionMapper.insertRoleMenu(rolePermissionDO);
		return responseVO;
	}

	@Override
	public ResponseVO updateByParameter(PermissionDO permissionDO){
		ResponseVO responseVO=new ResponseVO();
		if (permissionDO.getId()==null){
			responseVO.failure("id不能为空!");
			return responseVO;
		}
		//判断该权限名是否已使用
		PermissionDO permission=permissionMapper.checkPermission(permissionDO);
		if (permission!=null){
			//如果没有修改名称,查出来是自己,
			// 如果有修改名称, 根据ID来判断,相等就是自己,不相等就是该名称已被使用
			if (!permission.getId().equals(permissionDO.getId())){
				responseVO.failure("栏目已被使用!");
				return responseVO;
			}
		}
		//菜单有排序0:菜单 1:按钮
		if ("0".equals(permissionDO.getType())){
			//判断该排序序号是否被使用
			int count=permissionMapper.checkPermissionOrderNum(permissionDO);
			if (count>0){
				responseVO.failure("该排序已在上级页面中使用!");
				return responseVO;
			}
		}

		//更新栏目
		permissionMapper.updateByParameter(permissionDO);
		responseVO.success("修改栏目成功!");
		return responseVO;
	}

	@Override
	public List<PermissionDO> selectPermissionByUser(String username,String type) {
		return permissionMapper.selectPermissionByUser(username,type);
	}

	@Override
	public List<PermissionDO> selectPermissionByRoleId(Long roleId) {
		return permissionMapper.selectPermissionByRoleId(roleId);
	}

	private void buildTrees(List<TreeVO<PermissionDO>> trees, List<PermissionDO> menus, List<String> ids) {
		menus.forEach(menu -> {
			ids.add(menu.getId().toString());
			TreeVO treeVO = new TreeVO();
			BeanUtils.copyProperties(menu,treeVO);
			trees.add(treeVO);
		});
	}

	private void buildMenuTrees(List<TreeVO<PermissionVO>> trees, List<PermissionVO> menus) {
		menus.forEach(menu -> {
			TreeVO treeVO = new TreeVO();
			BeanUtils.copyProperties(menu,treeVO);
			trees.add(treeVO);
		});
	}

	@Override
	public Map<String, Object> selectUserMenuTreeList(String userName,String type) {
		Map<String, Object> result = new HashMap<>();
		List<PermissionDO> menus = permissionMapper.selectPermissionByUser(userName,type);
		if (menus == null || menus.size() == 0) {
			menus = new ArrayList<>();
		}
		//封装用户菜单权限数据
		List<PermissionVO> menuList=new ArrayList<>();
		for (PermissionDO permissionDO:menus){
			PermissionVO permissionVO=new PermissionVO();
			BeanUtils.copyProperties(permissionDO,permissionVO);	//复制参数
			menuList.add(permissionVO);
		}
		List<TreeVO<PermissionVO>> treeList = new ArrayList<>();
		buildMenuTrees(treeList, menuList);
		List<TreeVO<PermissionVO>> menuTree = TreeUtil.build(treeList);
//		List<ProjectDeviceDO> equipments=permissionMapper.getProjectDeviceByUserName(userName);
//		Map<String,String> map=buidPermissionEquipment(equipments);
//		if (map!=null){
//			result.put("equipments",map);
//		}
		result.put("menus", menuTree);
		return result;
	}

	@Override
	public Map<String, Object> selectPoleMenuTreeList() {
		Map<String, Object> result = new HashMap<>();
		PermissionDO auPermissionDO = new PermissionDO();
		List<PermissionDO> menus = permissionMapper.selectMenuList(auPermissionDO);
		if (menus == null || menus.size() == 0) {
			menus = new ArrayList<>();
		}
		List<String> ids = new ArrayList<>();
		List<TreeVO<PermissionDO>> treeList = new ArrayList<>();
		buildTrees(treeList, menus, ids);
		result.put("ids", ids);
		List<TreeVO<PermissionDO>> menuTree = TreeUtil.build(treeList);
		result.put("rows", menuTree);
		result.put("total", menus.size());
		return result;
	}

	@Override
	public ResponseVO addProjectDevice(List<ProjectDeviceDO> projectDeviceList) {
		ResponseVO responseVO=new ResponseVO();
		if (CollectionUtils.isEmpty(projectDeviceList)){
			responseVO.failure("数据不能为空!");
			return responseVO;
		}
		//获取用户ID,先删除原来配置信息
		for (ProjectDeviceDO projectDeviceDO:projectDeviceList){
			permissionMapper.deleteProjectDevice(projectDeviceDO.getUserId(),projectDeviceDO.getProjectId());
			break;
		}
		permissionMapper.insertProjectDevice(projectDeviceList);
		responseVO.success("项目设备权限配置成功!");
		return responseVO;
	}

	@Override
	public List<ProjectDeviceDO> getProjectDeviceByUserName(String userName,String projectId) {
		return permissionMapper.getProjectDeviceByUserName(userName, projectId);
	}

	private PermissionDO buildPermissionDO(PermissionDO permissionDO){
		PermissionDO permission=new PermissionDO();
		permission.setType("1");
		permission.setParentId(permissionDO.getId());
		permission.setPermissionName("查看");
		return permission;
	}

	private void getIds(List<String> promissionList,String id){
		//查询是否有子项目
		List<PermissionDO> list=permissionMapper.selectInfoByParentId(id);
		for (PermissionDO permissionDO:list){
			promissionList.add(permissionDO.getId().toString());
			//继续调用是否还有子项目数据
			this.getIds(promissionList,permissionDO.getId().toString());
		}

	}
}
