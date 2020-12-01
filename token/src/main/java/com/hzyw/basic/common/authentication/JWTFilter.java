package com.hzyw.basic.common.authentication;

import com.hzyw.basic.common.properties.AuProperties;
import com.hzyw.basic.common.service.RedisService;
import com.hzyw.basic.exception.RedisConnectException;
import com.hzyw.basic.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private RedisService redisService;


    private static final String TOKEN = "Authentication";

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse= (HttpServletResponse) response;
        //String contextPath = httpServletRequest.getContextPath();
        AuProperties auProperties = SpringContextUtil.getBean(AuProperties.class);
        String[] anonUrl = SplitUtil.splitByWholeSeparatorPreserveAllTokens(auProperties.getShiro().getAnonUrl(), ",");
        boolean match = false;
        //判断免校验的uri
        for (String u : anonUrl) {
            System.out.println(httpServletRequest.getRequestURI()+"=========");
            //match = true;
            //过滤swagger 请求
            if (pathMatcher.match(u, httpServletRequest.getRequestURI())) {
                if (httpServletRequest.getRequestURI().contains("login")
                        || httpServletRequest.getRequestURI().contains("registeredUsers")
                        || httpServletRequest.getRequestURI().contains("logout")
                        || httpServletRequest.getRequestURI().contains("getVerificationCode")
                        || httpServletRequest.getRequestURI().contains("swagger-ui.html")
                        || httpServletRequest.getRequestURI().contains("webjars")
                        || httpServletRequest.getRequestURI().contains("swagger-resources")
                        || httpServletRequest.getRequestURI().contains("v2")

                ) {
                    match = true;
                }
            }
        }
        if (match) {
            return true;
        }
        //判断session是否有效
       // checkSession(httpServletRequest,httpServletResponse);
//        Object tokenSession=httpServletRequest.getSession().getAttribute("token");
//        if (tokenSession==null){
//            try {
//                httpServletResponse.sendRedirect("http://192.168.3.183/login?redirect=%2F");    //http://192.168.3.183
//                return match;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            //throw new AuthenticationException("session.Invalid");
//        }
        //判断是否需要登陆 ,父类中已经判断
        if (isLoginAttempt(request, response)) {
            return executeLogin(request, response);
        }
        return false;
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader(TOKEN);
        return token != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(TOKEN);
        JWTToken jwtToken = new JWTToken(TokenUtil.decryptToken(token));

        try {
            getSubject(request, response).login(jwtToken);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            request.setAttribute("errorMessage",e.getMessage());
            return false;
        }
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个 option请求，这里我们给 option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        log.debug("Authentication required: sending 401 Authentication challenge response.");
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        //session已过期,跳转登录页面
//        if ("session.Invalid".equals(request.getAttribute("errorMessage"))){
//            try {
//                httpResponse.sendRedirect("/login?redirect=%2F");
//                return false;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
        httpResponse.setCharacterEncoding("utf-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        final String message = "未认证，请在前端系统进行认证";
        try (PrintWriter out = httpResponse.getWriter()) {
            String responseJson;
            if (request.getAttribute("errorMessage")==null){
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                responseJson = "{\"message\":\"" + message + "\",\"code\":\"401\"}";
            }else {
                responseJson = "{\"message\":\"" + request.getAttribute("errorMessage") + "\"}";
            }
            out.print(responseJson);
        } catch (IOException e) {
            log.error("sendChallenge error：", e);
        }
        return false;
    }

    private boolean checkSession(HttpServletRequest request,HttpServletResponse response){
        boolean isRight=false;
        String token = request.getHeader(TOKEN);
        try {
            Object tokenSession=request.getSession().getAttribute("token");
            if (tokenSession==null){
                    response.sendRedirect("http://192.168.3.183/login?redirect=%2F");    //http://192.168.3.183
                    return isRight;
            }
            //获取缓存中session信息
            String userName=redisService.get(TokenConstant.Cache.USER_SESSION_CACHE_PREFIX + request.getSession().getId());
            //token中解密获取用户名
            String username = JWTUtil.getUsername(TokenUtil.decryptToken(token));
            //判断是否为同一账户请求
            if (!userName.equals(userName)){
                response.sendRedirect("http://192.168.3.183/login?redirect=%2F");    //http://192.168.3.183
                return isRight;
            }
        } catch (IOException | RedisConnectException e) {
            e.printStackTrace();
        }
        isRight=true;
        return isRight;
    }
}
