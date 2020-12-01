package com.hzyw.basic.util;


/**
 * 常量接口类
 */
public interface TokenConstant {
    interface Page {
        //页面编号
        int defaultPageNum = 1;
        //每页记录条数
        int defaultPageSize = 10;

    }

    interface Cache {
        // token缓存前缀
        String TOKEN_CACHE_PREFIX = "cache.token.";
        // user缓存前缀
        String USER_CACHE_PREFIX = "cache.user.";
        // user角色缓存前缀
        String USER_ROLE_CACHE_PREFIX = "cache.user.role.";
        // user权限缓存前缀
        String USER_PERMISSION_CACHE_PREFIX = "cache.user.permission.";
        // 存储在线用户的 zset前缀
        String ACTIVE_USERS_ZSET_PREFIX = "cache.activeUser";
        // user Session缓存前缀
        String USER_SESSION_CACHE_PREFIX = "cache.user.session.";
        // user 数据权限缓存前缀
        String USER_EQUIPMENT_CACHE_PREFIX = "cache.user.equipmentPermissions.";
    }

    interface Common {
        String SPLIT_COMMA = ",";
        String SPLIT_DOT = ".";
        //请求成功返回码为：200
        String SUCCESS_CODE = "200";
        String SUCCESS_MSG = "请求成功";
        String USER_ANNOTATION_DATAAUTH = "com.hzyw.basic.util.DataAuth";
    }
}
