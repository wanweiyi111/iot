package com.hzyw.basic.dao.mapper;

import com.hzyw.basic.dos.UserDO;
import com.hzyw.basic.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/** queryByParameter
 * 用户接口类
 */
@Repository
@Mapper
public interface UserMapper {

	/**查询（根据主键ID查询*/
	List<UserVO> queryByParameter(@Param("userName") String userName, @Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

	int findAuUserTotalCount(@Param("userName") String userName);

	/**删除（根据主键ID删除）*/
	int deleteByParameter(List<String> userIds);

	/**添加*/
	int insertAuUserDO(@Param("auUser") UserDO auUser);

	/**查看是否已存在用户角色关系*/
	int checkUserRoleRelation(@Param("userId") Long userId,@Param("roleId") int roleId);

	/**保存用户角色关系*/
	int insertUserRoleRelation(@Param("userId") Long userId, @Param("roleId") int roleId);

	/**修改*/
	int updateByParameter(@Param("auUser") UserDO auUser);

	UserDO queryUserInfo(@Param("auUser") UserDO user);

	/**
	 * 根据用户名称查询用户
	 * @param userName
	 * @return
	 */
	UserVO selectUserByName(@Param("userName") String userName,@Param("userType") String userType);

	void resetPassword(@Param("auUser") UserDO auUser);
}
