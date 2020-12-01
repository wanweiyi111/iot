package com.hzyw.basic.mqtt.service;

import org.springframework.messaging.Message;

/**
 * @author male
 */
public interface MqttSubscribeService {

    /**
     * 订阅的主题列表
     * @return 订阅的主题列表
     */
    String[] listSubscribeTopics();

    /**
     * 处理订阅的消息
     * @param message 消息对象
     */
    void handleSubscribeMessage(Message message);
}