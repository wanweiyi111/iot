package com.hzyw.basic.util;

import com.alibaba.fastjson.JSONObject;
import com.hzyw.basic.config.Properties;
import com.hzyw.basic.domain.UserDO;
import com.hzyw.basic.domain.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author male
 * 用户工具类
 */
@Component
public class UserUtils {
    /**
     * 获取当前用户信息
     * @return 用户对象
     */
    public static UserDO getCurrentUser() {
        UserDTO userDTO = getCurrentUserDTO();
        return Optional.ofNullable(userDTO).orElse(new UserDTO()).getUser();
    }

    /**
     * 获取当前用户全部信息
     * @return 用户传输对象
     */
    public static UserDTO getCurrentUserDTO() {
        String url = Properties.TOKEN_URL + "?token=" + HttpUtils.getCurrentToken();
        String userJsonStr = HttpUtils.doGet(url);
        return parseUserJsonStr(userJsonStr);
    }

    private static UserDTO parseUserJsonStr(String userJsonStr) {
        UserDTO userDTO = null;

        if (StringUtils.isNotBlank(userJsonStr)) {
            JSONObject userJson = JSONObject.parseObject(userJsonStr);
            String responseCode = userJson.getString("code");
            if (HttpUtils.HTTP_OK.equals(responseCode)) {
                userDTO = userJson.getObject("data", UserDTO.class);
            }
        }

        return userDTO;
    }
}