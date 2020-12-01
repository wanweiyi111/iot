package com.hzyw.basic.util;

import com.alibaba.fastjson.JSONObject;
import com.hzyw.basic.common.service.RedisService;
import com.hzyw.basic.dos.ProjectEquipmentDO;
import com.hzyw.basic.exception.RedisConnectException;

import java.util.List;


public class PermissionUtil {

    public static List<ProjectEquipmentDO> getProjectDeviceByUserName(String userName){
        RedisService redisService= SpringContextUtil.getBean(RedisService.class);
        String permission="";
        try {
            //redis中获取数据权限信息
            permission=redisService.get(TokenConstant.Cache.USER_EQUIPMENT_CACHE_PREFIX + userName);
        } catch (RedisConnectException e) {
            e.printStackTrace();
        }
        return JSONObject.parseArray(permission, ProjectEquipmentDO.class);
    }




}
