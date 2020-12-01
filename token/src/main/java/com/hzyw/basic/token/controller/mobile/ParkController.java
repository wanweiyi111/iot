package com.hzyw.basic.token.controller.mobile;

import com.hzyw.basic.exception.Response;
import com.hzyw.basic.exception.SystemException;
import com.hzyw.basic.exception.TokenException;
import com.hzyw.basic.service.UserService;
import com.hzyw.basic.util.AutoResult;
import com.hzyw.basic.util.MD5Util;
import com.hzyw.basic.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Validated
@RestController
@Api(value = "智慧停车控制层")
@RequestMapping("/park")
public class ParkController {

    private static final String TOKEN = "Authentication";

    @Resource
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "登陆")
    public Response login(@RequestBody Map<String, String> paraMap, HttpServletResponse response, HttpServletRequest request) throws SystemException {
        String username = StringUtils.lowerCase(paraMap.get("userName"));
        String password = MD5Util.encrypt(username, "123");
        request.setAttribute("userType","1");
        Response res=userService.login(response,request,username,password);
        return res;
    }

    @PostMapping("/registeredUsers")
    @ApiOperation(value = "新增用户")
    @AutoResult
    public Response addUser(@RequestBody UserVO user) throws TokenException {
        user.setUserType("1");  //用户类型:1 智慧停车
        user.setPassWord("123");
        user.setProjectId(10020);
        user.setPassWord(MD5Util.encrypt(user.getUserName(), user.getPassWord()));
        Response response = userService.addUser(user);
        return response;
    }

    @PostMapping("/updatePassword")
    @ApiOperation(value = "修改密码")
    @AutoResult
    public Response updatePassword(@RequestBody Map<String,String> map) throws TokenException {
        map.put("userType","1");
        Response response = userService.changePassword(map);
        return response;
    }

    @GetMapping("/logout")
    @ApiOperation(value = "登出")
    @AutoResult
    public Response logout(HttpServletRequest request, HttpServletResponse resp) throws SystemException {
        Response response = userService.loginOut(request,resp);
        return response;
    }
}
