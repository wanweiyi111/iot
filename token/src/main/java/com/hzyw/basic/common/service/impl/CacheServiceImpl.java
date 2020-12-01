package com.hzyw.basic.common.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzyw.basic.common.service.CacheService;
import com.hzyw.basic.common.service.RedisService;
import com.hzyw.basic.dao.mapper.UserMapper;
import com.hzyw.basic.dos.PermissionDO;
import com.hzyw.basic.dos.RoleDO;
import com.hzyw.basic.dos.UserDO;
import com.hzyw.basic.service.PermissionService;
import com.hzyw.basic.service.RoleService;
import com.hzyw.basic.util.TokenConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("cacheService")
public class CacheServiceImpl implements CacheService {

    @Resource
    private RedisService redisService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService menuService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ObjectMapper mapper;

    @Override
    public void testConnect() throws Exception {
        this.redisService.exists("test");
    }

    @Override
    public UserDO getUser(String username) throws Exception {
        String userString = this.redisService.get(TokenConstant.Cache.USER_CACHE_PREFIX + username);
        if (StringUtils.isBlank(userString)) {
            throw new Exception();
        } else {
            return this.mapper.readValue(userString, UserDO.class);
        }
    }

    @Override
    public List<RoleDO> getRoles(String username) throws Exception {
        String roleListString = this.redisService.get(TokenConstant.Cache.USER_ROLE_CACHE_PREFIX + username);
        if (StringUtils.isBlank(roleListString)) {
            throw new Exception();
        } else {
            JavaType type = mapper.getTypeFactory().constructParametricType(List.class, UserDO.class);
            return this.mapper.readValue(roleListString, type);
        }
    }

    @Override
    public List<PermissionDO> getPermissions(String username) throws Exception {
        String permissionListString = this.redisService.get(TokenConstant.Cache.USER_PERMISSION_CACHE_PREFIX + username);
        if (StringUtils.isBlank(permissionListString)) {
            throw new Exception();
        } else {
            JavaType type = mapper.getTypeFactory().constructParametricType(List.class, PermissionDO.class);
            return this.mapper.readValue(permissionListString, type);
        }
    }


    @Override
    public void saveUser(UserDO user) throws Exception {
        String username = user.getUserName();
        this.deleteUser(username);
        redisService.set(TokenConstant.Cache.USER_CACHE_PREFIX + username, mapper.writeValueAsString(user));
    }

    @Override
    public void saveUser(String username,String userType) throws Exception {
        UserDO user = userMapper.selectUserByName(username,userType);
        this.deleteUser(username);
        redisService.set(TokenConstant.Cache.USER_CACHE_PREFIX + username, mapper.writeValueAsString(user));
    }

    @Override
    public void saveRoles(String username) throws Exception {
        List<RoleDO> roleList = roleService.selectRoleByUser(username);
        if (!roleList.isEmpty()) {
            this.deleteRoles(username);
            redisService.set(TokenConstant.Cache.USER_ROLE_CACHE_PREFIX + username, mapper.writeValueAsString(roleList));
        }

    }

    @Override
    public void savePermissions(String username) throws Exception {
        List<PermissionDO> permissionList = this.menuService.selectPermissionByUser(username,"1");
        if (!permissionList.isEmpty()) {
            this.deletePermissions(username);
            redisService.set(TokenConstant.Cache.USER_PERMISSION_CACHE_PREFIX + username, mapper.writeValueAsString(permissionList));
        }
    }


    @Override
    public void deleteUser(String username) throws Exception {
        username = username.toLowerCase();
        redisService.del(TokenConstant.Cache.USER_CACHE_PREFIX + username);
    }

    @Override
    public void deleteRoles(String username) throws Exception {
        username = username.toLowerCase();
        redisService.del(TokenConstant.Cache.USER_ROLE_CACHE_PREFIX + username);
    }

    @Override
    public void deletePermissions(String username) throws Exception {
        username = username.toLowerCase();
        redisService.del(TokenConstant.Cache.USER_PERMISSION_CACHE_PREFIX + username);
    }
}
