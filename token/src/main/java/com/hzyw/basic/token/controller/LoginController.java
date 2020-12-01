package com.hzyw.basic.token.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzyw.basic.common.authentication.JWTToken;
import com.hzyw.basic.common.properties.AuProperties;
import com.hzyw.basic.common.service.CacheService;
import com.hzyw.basic.common.service.RedisService;
import com.hzyw.basic.dos.LoginLogDO;
import com.hzyw.basic.dos.UserDO;
import com.hzyw.basic.exception.RedisConnectException;
import com.hzyw.basic.exception.Response;
import com.hzyw.basic.exception.SystemException;
import com.hzyw.basic.manager.UserManager;
import com.hzyw.basic.service.LoginLogService;
import com.hzyw.basic.service.PermissionService;
import com.hzyw.basic.service.UserService;
import com.hzyw.basic.util.*;
import com.hzyw.basic.vo.ActiveUserVO;
import com.hzyw.basic.vo.ResponseVO;
import com.hzyw.basic.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Validated
@RestController
@Api(value = "登陆接口")
@RequestMapping("/logon")
public class LoginController {

    private static final String TOKEN = "Authentication";

    @Resource
    private UserManager userManager;
    @Resource
    private UserService userService;
    @Resource
    private LoginLogService loginLogService;
    @Resource
    private AuProperties auproperties;
    @Resource
    private RedisService redisService;
    @Resource
    private CacheService cacheService;
    //    @Resource
//    private RoleService roleService;
    @Resource
    private ObjectMapper mapper;
    @Resource
    private PermissionService permissionService;
    @Resource
    private PermissionService menuService;


    @PostMapping("/test")
    @ApiOperation(value = "ceshi")
    public Response login(@RequestBody Map<String, String> paraMap){
        System.out.println("helloworld");
        String username = StringUtils.lowerCase(paraMap.get("username"));
        String password = MD5Util.encrypt(username, paraMap.get("password"));
        return null;
    }
    @PostMapping("/login")
    @ApiOperation(value = "登陆")
    public Response login(@RequestBody Map<String, String> paraMap, HttpServletResponse response, HttpServletRequest request) throws SystemException {
        Response res;
        HttpSession session = request.getSession();

        String username = StringUtils.lowerCase(paraMap.get("username"));
        String password = MD5Util.encrypt(username, paraMap.get("password"));
        String code = StringUtils.lowerCase(paraMap.get("code"));        //获取用户输入的验证码
        //获取用户登录时系统生成的验证码
        String verificationCode = "";
        if (session.getAttribute("imageCode") != null) {
            verificationCode = StringUtils.lowerCase(String.valueOf(session.getAttribute("imageCode")));
        }

        // 移动端不用验证码，这两个值设为一样，使后面代码的验证逻辑OK
        if (UserAgentUtils.isMobile(request)) {
            code = "123";
            verificationCode = "123";
        }
        code = "123";
        verificationCode = "123";

        String userType="0";
        UserVO user = this.userManager.getUser(username,userType);
        if (user == null) {
            //用户名错误
            res = new Response("403", "用户名错误");
            return res;
        }
        if (StringUtils.isEmpty(code)) {
            //验证码不能为空
            res = new Response("403", "请填写验证码!");
            return res;
        }
        if (StringUtils.isEmpty(verificationCode)) {
            //验证码不能为空
            res = new Response("403", "验证码已过期,请重新获取!");
            return res;
        }
        if (!StringUtils.equals(user.getPassWord(), password)) {
            //密码错误
            res = new Response("403", "密码错误");
            return res;
        }
        if (!StringUtils.equals(verificationCode, code)) {
            //验证码错误
            res = new Response("403", "验证码错误");
            return res;
        }
        if (UserDO.STATUS_LOCK.equals(user.getStatus())) {
            //账号已被锁定,请联系管理员！
            res = new Response("403", "账号已被锁定,请联系管理员！");
            return res;
        }


        // 更新用户登录时间
        userService.updateLoginTime(username);
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

        Map<String, Object> userInfo = this.generateUserInfo(jwtToken, user);
        //保存用户角色信息和权限信息到redis
        try {
            cacheService.saveRoles(username);
            cacheService.savePermissions(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        res = new Response("200", "认证成功！");
        res.setData(userInfo);
        return res;
    }


    @GetMapping("/logout")
    @ApiOperation(value = "登出")
    @AutoResult
    //@NotBlank(message = "{required}") String id,
    public ResponseVO logout(HttpServletRequest request, HttpServletResponse resp) throws SystemException {
        // TODO 待补充
        ResponseVO responseVO = new ResponseVO();
        try {
            HttpSession session = request.getSession();
            String ip = IPUtil.getIpAddr(request);
            String token = request.getHeader(TOKEN);
            //token解密
            String userToken = TokenUtil.decryptToken(token);
            //从 token中获取用户名
            String userName = JWTUtil.getUsername(userToken);
            String now = DateUtil.formatFullTime(LocalDateTime.now());
            //删除用户角色和权限缓存
            cacheService.deleteRoles(userName);
            cacheService.deletePermissions(userName);
            //从缓存中获取当前登陆用户信息集
            Set<String> userOnlineStringSet;
            userOnlineStringSet = redisService.zrangeByScore(TokenConstant.Cache.ACTIVE_USERS_ZSET_PREFIX, now, "+inf");
            ActiveUserVO kickoutUser = null;
            String kickoutUserString = "";
            //循环匹配
            for (String userOnlineString : userOnlineStringSet) {
                ActiveUserVO activeUser;
                activeUser = mapper.readValue(userOnlineString, ActiveUserVO.class);
                //如果用户名相等
                if (StringUtils.equals(activeUser.getUsername(), userName)) {
                    kickoutUser = activeUser;
                    kickoutUserString = userOnlineString;
                }
            }
            if (kickoutUser != null && StringUtils.isNotBlank(kickoutUserString)) {
                // 删除 zset中的记录
                redisService.zrem(TokenConstant.Cache.ACTIVE_USERS_ZSET_PREFIX, kickoutUserString);
                // 删除对应的 token缓存
                redisService.del(TokenConstant.Cache.TOKEN_CACHE_PREFIX + kickoutUser.getToken() + "." + kickoutUser.getUsername());
            }
            //清除缓存中session
            this.redisService.del(TokenConstant.Cache.USER_SESSION_CACHE_PREFIX + StringUtils.lowerCase(request.getSession().getId()));
            // 清空redis 中存储的数据权限信息，key = 前缀 + 用户名
            //this.redisService.del(TokenConstant.Cache.USER_EQUIPMENT_CACHE_PREFIX + userName);
            //清空session信息
            session.removeAttribute("imageCode");
            session.removeAttribute("token");
        } catch (RedisConnectException | JsonMappingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        responseVO.success("登出成功!");
        //session.invalidate();//清除 session 中的所有信息
        return responseVO;
    }


    private String saveTokenToRedis(UserVO user, JWTToken token, HttpServletRequest request) throws SystemException {
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
            // zset 存储登录用户，score 为过期时间戳
            this.redisService.zadd(TokenConstant.Cache.ACTIVE_USERS_ZSET_PREFIX, Double.valueOf(token.getExipreAt()), mapper.writeValueAsString(activeUser));
        } catch (RedisConnectException | JsonProcessingException e) {
            e.printStackTrace();
        }
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


    /**
     * 生成前端需要的用户信息，包括：
     * 1. token
     * 2. 用户角色
     * 3. 用户权限
     *
     * @param token token
     * @param user  用户信息
     * @return UserInfo
     */
    private Map<String, Object> generateUserInfo(JWTToken token, UserVO user) {
        String username = user.getUserName();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("token", token.getToken());
        userInfo.put("exipreTime", token.getExipreAt());

        Set<String> roles = this.userManager.getUserRoles(username);
        userInfo.put("roles", roles);

        Map<String, Object> permissions = this.userManager.getUserMenu(username, "0");
        userInfo.put("permissions", permissions);
        user.setPassWord("it's a secret");
        userInfo.put("user", user);
        userInfo.put("project", user.getProjectDOList());
        return userInfo;
    }


    @ApiOperation("生成验证码")
    @GetMapping("/getVerificationCode")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.createImage();
        //将验证码存入Session
        session.setAttribute("imageCode", objs[0]);

        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = null;
        try{
            os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IOException();
        }finally {
            os.close();
        }

    }


}
