package com.hzyw.basic.mqtt.service;

import com.alibaba.fastjson.JSON;
import com.hzyw.basic.util.Constant;
import com.hzyw.basic.util.HttpUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CheckRuleService {

    @Resource
    Environment environment;

    public void checkAlarmRules(Object object) {
        String url = environment.getProperty(Constant.CHECK_RULE_URL_KEY);
        String data= JSON.toJSONString(object);
        HttpUtils.doPost(url,data,false,15);
    }
}
