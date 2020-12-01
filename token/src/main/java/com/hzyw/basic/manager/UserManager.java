package com.hzyw.basic.manager;

import com.hzyw.basic.dos.PermissionDO;
import com.hzyw.basic.dos.RoleDO;
import com.hzyw.basic.dos.UserDO;
import com.hzyw.basic.service.PermissionService;
import com.hzyw.basic.service.RoleService;
import com.hzyw.basic.service.UserService;
import com.hzyw.basic.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 封装一些和 User相关的业务操作
 */
@Service
public class UserManager {

    /*@Resource
    private CacheService cacheService;*/
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService menuService;
    @Resource
    private UserService userService;


    /**
     * 通过用户名获取用户基本信息
     *
     * @param username 用户名
     * @return 用户基本信息
     */
    public UserVO getUser(String username, String userType) {
        return userService.selectUserByName(username,userType);
    }

    /**
     * 通过用户名获取用户角色集合
     *
     * @param username 用户名
     * @return 角色集合
     */
    public Set<String> getUserRoles(String username) {
        //TODO 缓存待加
        // 根据用户查询角色
        List<RoleDO> roleList =  roleService.selectRoleByUser(username);
        return roleList.stream().map(RoleDO::getRoleName).collect(Collectors.toSet());
    }
    /**
     * 通过用户名获取用户权限集合
     *
     * @param username 用户名
     * @return 权限集合
     */
    public Set<String> getUserPermissions(String username,String type) {
        List<PermissionDO> permissionList = menuService.selectPermissionByUser(username,type);
        return permissionList.stream().map(PermissionDO::getPerms).collect(Collectors.toSet());
    }

    /**
     * 通过用户名获取用户菜单树状集合
     *
     * @param username 用户名
     * @return 权限集合
     */
    public Map<String,Object> getUserMenu(String username,String type) {
        return menuService.selectUserMenuTreeList(username, "0");
    }


}
