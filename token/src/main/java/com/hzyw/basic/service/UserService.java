package com.hzyw.basic.service;

import com.hzyw.basic.dos.UserDO;
import com.hzyw.basic.exception.Response;
import com.hzyw.basic.exception.SystemException;
import com.hzyw.basic.vo.PageVO;
import com.hzyw.basic.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 用户接口类
 */
public interface UserService {

	/**查询（根据主键ID查询*/
	PageVO<UserVO> selectByParameter(String userName, int currentPage, int pageSize);

	/**
	 * 查询用户信息
	 * @param auUserVO
	 * @return
	 */
	UserDO queryUserInfo(UserDO auUserVO);

	/**删除（根据主键ID删除）*/
	void deleteUserByUserId(List<String> userIds);

	/**添加*/
	int insertAuUserDO(UserDO auUserT);

	//保存用户角色关系
	int insertUserRoleRelation(Long userId,int roleId);

	//查看是否已保存用户角色关系映射
	int checkUserRoleRelation(Long userId,int roleId);

	/**修改*/
	int updateByParameter(UserDO auUserT);

	/**
	 * 根据用户名称查询用户
	 * @param userName
	 * @return
	 */
	UserVO selectUserByName(String userName,String userType);

	/**
	 * 更新用户密码
	 * @param username 用户名
	 * @param password 新密码
	 */
	void updatePassword(String username, String password);

	/**
	 * 重置密码
	 * @param usernames 用户名称集合
	 */
	void resetPassword(String[] usernames);

	/**
	 * 更新最后更新时间
	 * @param username
	 */
	void updateLoginTime(String username) throws SystemException;

	/**
	 * 用户登录
	 * @param response
	 * @param request
	 * @param username
	 * @param password
	 * @return
	 */
	Response login(HttpServletResponse response, HttpServletRequest request,String username,String password);

	/**
	 * 注册新用户
	 * @param user
	 * @return
	 */
	Response addUser(UserVO user);

	Response changePassword(Map<String,String> map);

	Response loginOut(HttpServletRequest request, HttpServletResponse resp);

}
