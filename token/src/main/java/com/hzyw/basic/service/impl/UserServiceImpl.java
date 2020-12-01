package com.hzyw.basic.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzyw.basic.common.authentication.JWTToken;
import com.hzyw.basic.common.properties.AuProperties;
import com.hzyw.basic.common.service.CacheService;
import com.hzyw.basic.common.service.RedisService;
import com.hzyw.basic.dao.mapper.UserMapper;
import com.hzyw.basic.dos.LoginLogDO;
import com.hzyw.basic.dos.UserDO;
import com.hzyw.basic.exception.RedisConnectException;
import com.hzyw.basic.exception.Response;
import com.hzyw.basic.exception.SystemException;
import com.hzyw.basic.manager.UserManager;
import com.hzyw.basic.service.LoginLogService;
import com.hzyw.basic.service.UserService;
import com.hzyw.basic.util.*;
import com.hzyw.basic.vo.ActiveUserVO;
import com.hzyw.basic.vo.PageVO;
import com.hzyw.basic.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;


/**
 * 用户接口类
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private static final String TOKEN = "Authentication";

	@Resource
	private UserMapper userMapper;
	@Resource
	private CacheService cacheService;
	@Resource
	private LoginLogService loginLogService;
	@Resource
	private AuProperties auproperties;
	@Resource
	private RedisService redisService;
	@Resource
	private UserManager userManager;
	@Resource
	private ObjectMapper mapper;

	@Override
	public PageVO<UserVO> selectByParameter(String userName, int currentPage, int pageSize){
		int totalCount = userMapper.findAuUserTotalCount(userName);
		int pageNo = (currentPage - 1) * pageSize;
		PageVO<UserVO> pageResult = new PageVO<>(currentPage, pageSize, totalCount);
		pageResult.setCode(200);
		pageResult.setMessage("SUCCESS");
		List<UserVO> resultList = userMapper.queryByParameter(userName, pageNo, pageSize);
		if (resultList == null || resultList.size() == 0) {
			resultList = new ArrayList<>();
		}
		pageResult.setData(resultList);
		return pageResult;
	}

	@Override
	public UserDO queryUserInfo(UserDO auUserVO) {
		return userMapper.queryUserInfo(auUserVO);
	}

	@Override
	public void deleteUserByUserId(List<String> userIds){
		userMapper.deleteByParameter(userIds);
	}

	@Override
	public int insertAuUserDO(UserDO auUserT){
		return userMapper.insertAuUserDO(auUserT);
	}

	@Override
	public int insertUserRoleRelation(Long userId, int roleId) {
		return userMapper.insertUserRoleRelation(userId,roleId);
	}

	@Override
	public int checkUserRoleRelation(Long userId, int roleId) {
		return userMapper.checkUserRoleRelation(userId,roleId);
	}

	@Override
	public int updateByParameter(UserDO auUserT){
		return userMapper.updateByParameter(auUserT);
	}

	@Override
	public UserVO selectUserByName(String userName,String userType) {
		return userMapper.selectUserByName(userName,userType);
	}

	@Override
	public void updatePassword(String username, String password) {
		UserDO user = new UserDO();
		user.setPassWord(MD5Util.encrypt(username, password));
		user.setUserName(username);
		userMapper.resetPassword(user);

	}

	@Override
	public void resetPassword(String[] usernames) {
		for (String username : usernames) {

			UserDO user = new UserDO();
			user.setPassWord(MD5Util.encrypt(username, UserDO.DEFAULT_PASSWORD));
			user.setUserName(username);
			userMapper.resetPassword(user);
		}

	}
	@Override
	public void updateLoginTime(String username) throws SystemException {
		UserDO auUser = new UserDO();
		auUser.setLastLoginTime(new Date());
		userMapper.updateByParameter(auUser);

//		cacheService.saveUser(username);
	}

	@Override
	public Response login(HttpServletResponse response, HttpServletRequest request, String username, String password) {
		Response res;
		HttpSession session = request.getSession();
		UserDO user = this.userManager.getUser(username,request.getAttribute("userType").toString());
		if (user == null) {
			//用户名错误
			res = new Response("403", "用户名错误");
			return res;
		}
		if (!StringUtils.equals(user.getPassWord(), password)) {
			//密码错误
			res = new Response("403", "密码错误");
			return res;
		}
		if (UserDO.STATUS_LOCK.equals(user.getStatus())) {
			//账号已被锁定,请联系管理员！
			res = new Response("403", "账号已被锁定,请联系管理员！");
			return res;
		}


		// 更新用户登录时间
		updateLoginTime(username);
		// 保存登录记录
		LoginLogDO loginLogDO = new LoginLogDO();
		loginLogDO.setUserName(username);
		loginLogService.insertAuLoginLogDO(loginLogDO);

		String token = TokenUtil.encryptToken(JWTUtil.sign(username, password));
		LocalDateTime expireTime = LocalDateTime.now().plusSeconds(auproperties.getShiro().getJwtTimeOut());
		String expireTimeStr = DateUtil.formatFullTime(expireTime);     //生成失效时间
		JWTToken jwtToken = new JWTToken(token, expireTimeStr);

		Cookie cookie = new Cookie("token", token);
		response.addCookie(cookie);
		session.setAttribute("token", token);

		//保存Token和session到redis
		String userId = this.saveTokenToRedis(user, jwtToken, request);
		user.setId(Long.parseLong(userId));

		//Map<String, Object> userInfo = this.generateUserInfo(jwtToken, user);
		Map<String, Object> userInfo=new HashMap<>();
		user.setPassWord("");
		userInfo.put("userInfo",user);
		userInfo.put("token",jwtToken.getToken());
		res = new Response("200", "认证成功！");
		res.setData(userInfo);
		return res;
	}

	@Override
	public Response addUser(UserVO user) {
		Response res = null;
		try {
			String mgs = validationUser(user);
			if (StringUtils.isNotEmpty(mgs)) {
				res = new Response("400", mgs);
				return res;
			}
			//默认生成8位随机密码(含大小写字母，数字的组合)
			if (user.getUserType().equals("0")){
				user.setPassWord(PassWordUtil.generatePassWord(8));
			}
			UserDO auUserVO = queryUserInfo(user);
			if (auUserVO != null) {
				res = new Response("400", "该用户已存在");
				return res;

			}
			int insertRows = insertAuUserDO(user);
			//物联网用户判断---start---
			if (user.getUserType().equals("0")){
				int count = checkUserRoleRelation(user.getId(), user.getRoleId());
				if (count < 1) {
					insertUserRoleRelation(user.getId(), user.getRoleId());
				}
			}
			//----end----
			res = insertRows > 0 ? new Response("200", "新增用户成功") :
					new Response("500", "新增用户失败");
		} catch (Exception e) {
			log.error("新增用户失败", e);
			res = new Response("500", "新增用户失败：" + e);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public Response changePassword(Map<String, String> map) {
		Response res ;
		if (map==null){
			res=new Response("400","参数不能为空");
			return res;
		}
		String userName=map.get("userName");
		String userType=map.get("userType");
		String oldPassword=map.get("oldPassword");
		String newPassword=map.get("newPassword");
		String confirmPassword=map.get("confirmPassword");
		//校验
		String message=validationPassword(userName,oldPassword,newPassword,confirmPassword);
		if (!StringUtils.isEmpty(message)){
			res=new Response("400",message);
			return res;
		}
		//根据用户名获取用户原数据
		UserDO user = selectUserByName(userName,userType);
		//加密
		String encryptPassword = MD5Util.encrypt(userName, oldPassword);
		if (user == null) {
			res=new Response("400","该用户不存在!");
			return res;
		}
		if (user != null) {
			 if (!StringUtils.equals(user.getPassWord(), encryptPassword)){
				 res=new Response("400","原始密码不正确!");
				 return res;
			 }
		}
		UserDO userDO = new UserDO();
		userDO.setPassWord(MD5Util.encrypt(userName, newPassword));
		userDO.setUserName(userName);
		userDO.setUserType(userType);
		userMapper.resetPassword(userDO);
		res = new Response("200", "修改密码成功!");
		return res;
	}

	@Override
	public Response loginOut(HttpServletRequest request, HttpServletResponse resp) {
		Response response =new Response();
		try {
			HttpSession session = request.getSession();
			String ip = IPUtil.getIpAddr(request);
			String token = request.getHeader(TOKEN);
			//token解密
			String userToken = TokenUtil.decryptToken(token);
			//从 token中获取用户名
			String userName = JWTUtil.getUsername(userToken);
			String now = DateUtil.formatFullTime(LocalDateTime.now());
			// 删除对应的 token缓存
			redisService.del(TokenConstant.Cache.TOKEN_CACHE_PREFIX + token + "." + ip);
			//清除缓存中session
			this.redisService.del(TokenConstant.Cache.USER_SESSION_CACHE_PREFIX + StringUtils.lowerCase(request.getSession().getId()));
			//清空session信息
			session.removeAttribute("token");
		} catch (RedisConnectException  e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		response = new Response("200", "登出成功!");
		return response;
	}

	private String saveTokenToRedis(UserDO user, JWTToken token, HttpServletRequest request) throws SystemException {
		String ip = IPUtil.getIpAddr(request);
		String sessionId = StringUtils.lowerCase(request.getSession().getId());

		// 构建在线用户
		ActiveUserVO activeUser = new ActiveUserVO();
		activeUser.setId(CommonUtil.getRandom(11));
		activeUser.setUsername(user.getUserName());
		activeUser.setIp(ip);
		activeUser.setToken(token.getToken());
		//地址待补充 TODO
		activeUser.setLoginAddress(null);

		try {
			// redis 中存储这个加密 token，key = 前缀 + 加密 token + .ip
			this.redisService.set(TokenConstant.Cache.TOKEN_CACHE_PREFIX + token.getToken() + TokenConstant.Common.SPLIT_DOT + ip, token.getToken(), auproperties.getShiro().getJwtTimeOut() * 1000);
		} catch (RedisConnectException e) {
			e.printStackTrace();
		}
		try {
			// redis 中存储session，key = sessionId
			this.redisService.del(TokenConstant.Cache.USER_SESSION_CACHE_PREFIX + sessionId);
			this.redisService.set(TokenConstant.Cache.USER_SESSION_CACHE_PREFIX + sessionId, user.getUserName());
		} catch (RedisConnectException e) {
			e.printStackTrace();
		}
		return activeUser.getId();
	}

	public String validationUser(UserDO user) {
		String message = "";
		if (StringUtils.isEmpty(user.getUserName())) {
			return "用户名不能为空!";
		}

		//智慧停车用户
		if (user.getUserType().equals("1")){
			if (StringUtils.isEmpty(user.getCarNumber())){
				return "车牌号不能为空!";
			}
		}
		return message;
	}

	public String validationPassword(String userName,String oldPassword,String newPassword,String confirmPassword ){
		String message = "";
		if (StringUtils.isEmpty(userName)){
			return "用户名不能为空!";
		}
		if (StringUtils.isEmpty(oldPassword)){
			return "原密码不能为空!";
		}
		if (StringUtils.isEmpty(newPassword)){
			return "新密码不能为空!";
		}
		if (StringUtils.isEmpty(confirmPassword)){
			return "再次输入登录密码不能为空!";
		}
		if (!StringUtils.equals(newPassword,confirmPassword)){
			return "两次密码不同,请再次输入登录密码!";
		}
		return message;
	}

}
