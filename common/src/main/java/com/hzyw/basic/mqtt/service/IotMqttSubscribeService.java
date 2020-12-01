package com.hzyw.basic.mqtt.service;

import com.hzyw.basic.util.EquipmentEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import java.util.Objects;

/**
 * @author male
 */
public interface IotMqttSubscribeService {

    Logger log = LoggerFactory.getLogger(MqttSubscribeService.class);

    /**
     * 处理设备运行上报数据
     * @param data 消息载体
     */
    void handlerRuntimeMessage(String data);

    /**
     * 处理设备初始化数据
     * @param data 消息载体
     */
    void handlerEquipmentInfoMessage(String data);

    /**
     * 处理IOT上报数据
     * @param message MQTT消息
     * @param equipmentEnum 设备类型
     */
    default void handleIotMessage(Message message, EquipmentEnum equipmentEnum) {
        String topic = Objects.requireNonNull(message.getHeaders().get("mqtt_receivedTopic")).toString();
        String data = message.getPayload().toString();
        log.info("received you message:" + data);
        log.info("received you topic:" + topic);
        if (equipmentEnum.getRuntimeTopic().equals(topic)) {
            handlerRuntimeMessage(data);
        } else if (equipmentEnum.getEquipmentInfoTopic().equals(topic)) {
            handlerEquipmentInfoMessage(data);
        }
    }
}