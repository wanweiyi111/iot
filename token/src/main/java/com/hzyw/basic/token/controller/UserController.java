package com.hzyw.basic.token.controller;

import com.hzyw.basic.dos.ProjectDeviceDO;
import com.hzyw.basic.dos.UserDO;
import com.hzyw.basic.exception.Response;
import com.hzyw.basic.exception.TokenException;
import com.hzyw.basic.manager.UserManager;
import com.hzyw.basic.service.PermissionService;
import com.hzyw.basic.service.UserService;
import com.hzyw.basic.util.*;
import com.hzyw.basic.vo.PageVO;
import com.hzyw.basic.vo.ResponseVO;
import com.hzyw.basic.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Slf4j
@Validated
@RestController
@Api(value = "用户接口")
@RequestMapping("/user")
public class UserController {

    private String message;

    @Resource
    private UserService userService;

    @Resource
    private UserManager userManager;
    @Resource
    private PermissionService permissionService;

    @GetMapping("/query/list/{currentPage}/{pageSize}")
    @ApiOperation(value = "获取用户信息")
    @AutoResult
    public Response queryUserListInfo(@RequestParam(value = "userName", required = true) String userName,
                                            @PathVariable("currentPage") int currentPage,
                                            @PathVariable("pageSize") int pageSize) {
        Response res = new Response();
        if(userName == null){
            res.setCode("400");
            res.setMsg("用户名参数为空");
        }else{
            res.setData(userService.selectByParameter(userName, currentPage, pageSize));
        }
        return res;
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param
     * @return
     */
    @GetMapping("/getUserInfoById/{userId}")
    @ApiOperation(value = "根据用户ID查询用户信息")
    @AutoResult
    public UserDO getUserInfoById(@PathVariable("userId") String userId) {
        UserDO auUserVO = new UserDO();
        auUserVO.setId(Long.parseLong(userId));
        return this.userService.queryUserInfo(auUserVO);
    }

    /**
     * 检查是否存在该用户名
     *
     * @param
     * @return
     */
    @GetMapping("/getUserInfoByName")
    @ApiOperation(value = "检查是否存在该用户名")
    @AutoResult
    public UserDO getUserInfoByName(@RequestParam("userName") String userName) {
        UserDO auUserVO = new UserDO();
        auUserVO.setUserName(userName);
        return this.userService.queryUserInfo(auUserVO);
    }


    /**
     * 根据用户名称查询用户
     *
     * @param userName
     * @return
     */
    @GetMapping("/check/{userName}")
    @ApiOperation(value = "根据用户名称查询用户")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String userName) {
        String userType="0";
        return this.userService.selectUserByName(userName,userType) == null;

    }

    /**
     * 根据token换取用户信息
     *
     * @param token
     * @return
     */
    @GetMapping("/getUserInfoByToken")
    @ApiOperation(value = "根据token换取用户信息")
    public Response getUserInfoByToken(@RequestParam("token") String token) {
        Response res = new Response();
        if (StringUtils.isEmpty(token)) {
            res.setMsg("token不能为空!");
            res.setCode("400");
            return res;
        }
        //token解密
        String userToken = TokenUtil.decryptToken(token);
        //从 token中获取用户名
        String userName = JWTUtil.getUsername(userToken);
        //封装数据
        Map<String, Object> userInfo = this.generateUserInfo(token, userName);
        res.setCode("200");
        res.setMsg("请求成功!");
        res.setData(userInfo);
        return res;

    }

    @PostMapping("/addUser")
    //@RequiresPermissions(value = "user:add")
    @ApiOperation(value = "新增用户")
    @AutoResult
    public ResponseVO addUser(@RequestBody UserVO user) throws TokenException {
        ResponseVO responseVO = new ResponseVO();
        try {
            String mgs = validationUser(user, 1);
            if (StringUtils.isNotEmpty(mgs)) {
                responseVO.setCode("400");
                responseVO.setMessage(mgs);
                return responseVO;
            }

            user.setUserType("0");
            UserDO auUserVO = userService.queryUserInfo(user);
            if (auUserVO != null) {
                responseVO.setCode("400");
                responseVO.setMessage("该用户已存在!");
                return responseVO;
            }

            String password = user.getPassWord();
            String confirmPassword = user.getConfirmPassword();
            if (StringUtils.isNotBlank(password)) {
                if (password.equals(confirmPassword)) {
                    user.setPassWord(MD5Util.encrypt(user.getUserName(), password));
                } else {
                    responseVO.setCode("400");
                    responseVO.setMessage("密码和确认密码不一致");
                    return responseVO;
                }
            } else {
                user.setPassWord(MD5Util.encrypt(user.getUserName(), "123"));
            }

            this.userService.insertAuUserDO(user);
            UserDO userDO = userService.queryUserInfo(user);
            user.setId(userDO.getId());
            int count = this.userService.checkUserRoleRelation(user.getId(), user.getRoleId());
            if (count < 1) {
                this.userService.insertUserRoleRelation(user.getId(), user.getRoleId());
            }
            responseVO.setCode("200");
            responseVO.setMessage("新增用户成功!");
        } catch (Exception e) {
            message = "新增用户失败";
            log.error(message, e);
            e.printStackTrace();
            throw new TokenException(message);
        }
        return responseVO;
    }

    @PostMapping("/updateUser")
    //@RequiresPermissions("user:update")
    @ApiOperation(value = "修改用户")
    @AutoResult
    public ResponseVO updateUser(@RequestBody UserVO user, @RequestHeader("Authentication") String token) throws TokenException {
        ResponseVO responseVO = new ResponseVO();
        try {
            if (!StringUtils.isEmpty(user.getPassWord())) {
                user.setPassWord(MD5Util.encrypt(user.getUserName(), user.getPassWord()));
            }
//            //token解密
//            String userToken = TokenUtil.decryptToken(token);
//            //从 token中获取用户名
//            String userName = JWTUtil.getUsername(userToken);
//            user.setUserName(userName);
            this.userService.updateByParameter(user);
            responseVO.setCode("200");
            responseVO.setMessage("修改用户成功!");
        } catch (Exception e) {
            message = "修改用户失败";
            log.error(message, e);
            throw new TokenException(message);
        }
        return responseVO;
    }

    @DeleteMapping("/deleteUser")
    //@RequiresPermissions("user:delete")
    @ApiOperation(value = "删除用户")
    @AutoResult
    public ResponseVO deleteUsers(@NotBlank(message = "{required}") @RequestParam("userIds") String userIds) {
        ResponseVO responseVO = new ResponseVO();
        try {
            if (StringUtils.isEmpty(userIds)) {
                responseVO.setCode("400");
                responseVO.setMessage("用户ID不能为空!");
                return responseVO;
            }
            String[] ids = userIds.split(TokenConstant.Common.SPLIT_COMMA);
            List<String> userIdList = Arrays.asList(ids);
            userService.deleteUserByUserId(userIdList);
        } catch (Exception e) {
            log.error(message, e);
            responseVO.setCode("400");
            responseVO.setMessage("删除用户失败!");
            return responseVO;
        }
        responseVO.setCode("200");
        responseVO.setMessage("删除用户成功!");
        return responseVO;
    }

    /**
     * 校验用户密码
     *
     * @param username
     * @param password
     * @return
     */
    @GetMapping("password/check")
    @ApiOperation(value = "校验用户密码")
    @AutoResult
    public boolean checkPassword(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) {
        String encryptPassword = MD5Util.encrypt(username, password);
        String userType="0";
        UserDO user = userService.selectUserByName(username,userType);
        if (user != null) {
            return StringUtils.equals(user.getPassWord(), encryptPassword);
        } else {
            return false;
        }

    }

    /**
     * 更新用户密码
     *
     * @param username
     * @param password
     * @throws TokenException
     */
    @PutMapping("password")
    @ApiOperation(value = "更新用户密码")
    @AutoResult
    public void updatePassword(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) throws TokenException {
        try {
            userService.updatePassword(username, password);
        } catch (Exception e) {
            message = "修改密码失败";
            log.error(message, e);
            throw new TokenException(message);
        }
    }

    /**
     * 重置密码
     *
     * @param usernames
     * @throws TokenException
     */
    @PutMapping("password/reset")
    @ApiOperation(value = "重置密码")
    //@RequiresPermissions("user:reset")
    public void resetPassword(@NotBlank(message = "{required}") String usernames) throws TokenException {
        try {
            String[] usernameArr = usernames.split(TokenConstant.Common.SPLIT_COMMA);
            this.userService.resetPassword(usernameArr);
        } catch (Exception e) {
            message = "重置用户密码失败";
            log.error(message, e);
            throw new TokenException(message);
        }
    }

    /**
     * @param user status 1:新增 2:修改
     * @return
     */
    public String validationUser(UserVO user, int status) {
        String message = "";
        if (StringUtils.isEmpty(user.getUserName())) {
            return "用户名不能为空!";
        }
        if (user.getDeptId() == null) {
            return "关联部门不能为空!";
        }
        //新增才校验角色和项目
        if (status == 1) {
            if (user.getRoleId() == null) {
                return "关联角色不能为空!";
            }
            if (user.getProjectId() == null) {
                return "关联项目不能为空!";
            }
        }
        if (StringUtils.isNotEmpty(user.getEmail())) {
            if (!user.getEmail().matches("[\\w.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
                return "请填写正确的邮箱格式!";
            }
        }
        return message;
    }

    private Map<String, Object> generateUserInfo(String token, String userName) {
        //String username = user.getUserName();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("token", token);
        UserVO user = this.userManager.getUser(userName,"0");
        Set<String> roles = this.userManager.getUserRoles(userName);
        userInfo.put("roles", roles);

        Set<String> permissions = this.userManager.getUserPermissions(userName, "");
        userInfo.put("permissions", permissions);
        user.setPassWord("it's a secret");
        userInfo.put("user", user);
        userInfo.put("project", user.getProjectDOList());
        List<ProjectDeviceDO> projectDeviceDOList = permissionService.getProjectDeviceByUserName(user.getUserName(), user.getProjectId().toString());
        List<String> list = new ArrayList<>();
        for (ProjectDeviceDO projectDeviceDO : projectDeviceDOList) {
            String promission = projectDeviceDO.getEquipmentId();
            if (!org.springframework.util.StringUtils.isEmpty(promission)){
                list.add(promission);
            }
        }
        userInfo.put("equipmentcodes",list);
        return userInfo;
    }


}
