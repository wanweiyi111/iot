package com.hzyw.basic.mqtt.service;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * 发送Mqtt消息网关接口
 * @author haoyuan
 * date 2019.08.07
 */
@SuppressWarnings("unused")
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttPublishService {
    /**
     * 给默认主题发送消息
     *
     * @param data 消息
     */
    void sendToMqtt(String data);

    /**
     * 指定消息头发送消息
     * @param payload 消息体
     * @param topic 消息头
     */
    void sendToMqtt(String payload, @Header(MqttHeaders.TOPIC) String topic);

    /**
     * 指定消息头和消息机制发送消息
     * @param topic 消息头
     * @param qos 消息机制
     * @param payload 消息体
     */
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);


}
