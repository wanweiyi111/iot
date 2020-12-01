package com.hzyw.basic.dao.mapper;

import com.hzyw.basic.dos.PermissionDO;
import com.hzyw.basic.dos.ProjectDeviceDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限接口类
 */
@Repository
@Mapper
public interface PermissionMapper{

	/**查询（根据主键ID查询*/
	List<PermissionDO>  selectByParameter(@Param("permission") PermissionDO permission, @Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

	int selectByParameterCount(@Param("permission") PermissionDO permission);

	/**删除（根据主键ID删除）*/
	int deleteByParameter(List<String> list);

	//查找已参数ID作为父类id的数据
	List<PermissionDO> selectInfoByParentId(@Param("parentId") String parentId);

	/**添加权限*/
	int insertPermission(@Param("auPermission") PermissionDO auPermission);

	/**修改*/
	int updateByParameter(@Param("auPermission") PermissionDO auPermission);

	/**查询菜单列表*/
	List<PermissionDO>  selectMenuList(PermissionDO auPermissionDO);

	//根据用户查询权限集合
	List<PermissionDO> selectPermissionByUser(@Param("username") String username,@Param("type")String type);

	//根据角色ID查询权限
	List<PermissionDO> selectPermissionByRoleId(Long roleId);

	//查询该菜单下子菜单某排序序号是否被使用
	int checkPermissionOrderNum(@Param("permission") PermissionDO auPermission);

	//查看该权限菜单是否已使用
	PermissionDO checkPermission(@Param("permission") PermissionDO auPermission);

	//根据用户ID先删除原设备配置权限
	int deleteProjectDevice(@Param("userId") long userId,@Param("projectId") String projectId);

	//保存配置的用户设备数据权限
	int insertProjectDevice(List<ProjectDeviceDO> projectDeviceList);

	//根据用户名获取配置的设备数据权限
	List<ProjectDeviceDO> getProjectDeviceByUserName(@Param("userName") String userName,@Param("projectId") String projectId);
}
