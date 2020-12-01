package com.hzyw.basic.common.authentication;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzyw.basic.common.service.RedisService;
import com.hzyw.basic.dos.PermissionDO;
import com.hzyw.basic.exception.RedisConnectException;
import com.hzyw.basic.manager.UserManager;
import com.hzyw.basic.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * 自定义实现 ShiroRealm，包含认证和授权两大模块
 *
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {


    @Resource
    private RedisService redisService;

    @Resource
    private ObjectMapper mapper;

    @Resource
    private UserManager userManager;

    private static final String TOKEN = "Authentication";

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**`
     * 授权模块，获取用户角色和权限
     *
     * @param token token
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
        String username = JWTUtil.getUsername(token.toString());

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 获取用户角色集
        Set<String> roleSet = userManager.getUserRoles(username);
        simpleAuthorizationInfo.setRoles(roleSet);

        // 获取用户权限集
        Set<String> permissionSet = userManager.getUserPermissions(username,"1");
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     *
     * @param authenticationToken 身份认证 token
     * @return AuthenticationInfo 身份认证信息
     * @throws AuthenticationException 认证相关异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 这里的 token是从 JWTFilter 的 executeLogin 方法传递过来的，已经经过了解密
        String token = (String) authenticationToken.getCredentials();
        log.info("身份认证 token:"+token);
        // 从 redis里获取这个 token
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        //session后期做
//        try {
//            checkSession(request);
//        } catch (RedisConnectException e) {
//            e.printStackTrace();
//        }
        String ip = IPUtil.getIpAddr(request);
        String username = JWTUtil.getUsername(token);
        String encryptToken = TokenUtil.encryptToken(token);
        log.info("解密encryptToken:"+encryptToken+"-----  ip:"+ip);
        String encryptTokenInRedis;

        try {
            log.info("获取encryptTokenInRedis前: "+TokenConstant.Cache.TOKEN_CACHE_PREFIX + encryptToken + "." + ip);
            //encryptTokenInRedis = redisService.get(TokenConstant.Cache.TOKEN_CACHE_PREFIX + encryptToken + "." + username);
            encryptTokenInRedis = redisService.get(TokenConstant.Cache.TOKEN_CACHE_PREFIX + encryptToken + "." + ip);
            log.info("redis.encryptTokenInRedis:"+encryptTokenInRedis);
        } catch (Exception ignore) {
            throw new AuthenticationException("从Redis获取缓存失败！");
        }
        // 如果找不到，说明已经失效
        if (isBlank(encryptTokenInRedis)) {
            log.info("encryptTokenInRedis为空!");
            log.info(TokenConstant.Cache.TOKEN_CACHE_PREFIX + encryptToken + "." + ip);
            throw new AuthenticationException("token已经过期！");
        }


        if (isBlank(username)) {
            throw new AuthenticationException("根据用户名校验token不通过！");
        }
        
        if (!encryptToken.equals(encryptTokenInRedis)){
            throw new AuthenticationException("token无效！");
        }

        //获取用户权限信息
//        List<PermissionDO> permissions;
//        try {
//            permissions=getPermissions(username);
//        } catch (Exception e) {
//            throw new AuthenticationException("从Redis获取缓存失败！");
//        }
//        int count=0;
//        try {
//            if (CollectionUtils.isEmpty(permissions)) {
//                throw new AuthenticationException("权限已经过期！");
//            }
//            String uri= request.getRequestURI();
//            //权限判断
//            for (PermissionDO permissionDO:permissions){
//                //非空
//                if (!StringUtils.isEmpty(permissionDO.getPerms())){
//                    if (permissionDO.getPerms().contains(uri) || uri.contains(permissionDO.getPerms())){
//                        count++;
//                        break;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new AuthenticationException("验证方法权限失败！");
//        }
//        if (count==0){
//            throw new AuthenticationException("没有该方法权限,无法访问！");
//        }

        return new SimpleAuthenticationInfo(token, token, "custom_shiro_realm");
    }


    public void checkSession(HttpServletRequest request) throws RedisConnectException {

        String token = request.getHeader(TOKEN);
       // try {
            Object tokenSession=request.getSession().getAttribute("token");
            if (tokenSession==null){
                throw new AuthenticationException("session.Invalid");
                //response.sendRedirect("/login?redirect=%2F");    //http://192.168.3.183
            }
            //获取缓存中session信息
            String userName=redisService.get(TokenConstant.Cache.USER_SESSION_CACHE_PREFIX + request.getSession().getId());
            //token中解密获取用户名
            String username = JWTUtil.getUsername(TokenUtil.decryptToken(token));
            //判断是否为同一账户请求
            if (userName==null){
                throw new AuthenticationException("session.Invalid");       //该session已失效
            }
            if (!userName.equals(userName)){
                throw new AuthenticationException("session.Invalid");       //不是同一用户,检验不通过
                //response.sendRedirect("/login?redirect=%2F");    //http://192.168.3.183
            }
        //} //catch (IOException e) {
           // e.printStackTrace();
       // } //catch (RedisConnectException e) {
           // e.printStackTrace();
       // }
    }

    public List<PermissionDO> getPermissions(String username) throws Exception {
        String permissionListString = this.redisService.get(TokenConstant.Cache.USER_PERMISSION_CACHE_PREFIX + username);
        if (isBlank(permissionListString)) {
            throw new Exception();
        } else {
            JavaType type = mapper.getTypeFactory().constructParametricType(List.class, PermissionDO.class);
            return this.mapper.readValue(permissionListString, type);
        }
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

}
