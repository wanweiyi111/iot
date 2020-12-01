package com.hzyw.basic.mqtt;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author male
 */
@Getter
@AllArgsConstructor
public enum IotTopicEnum {
    /**
     * 设备初始化的主题
     */
    EQUIPMENT_INFO_TOPIC("EquipmentInfo"),

    /**
     * 设备运行时的主题
     */
    RUNTIME_TOPIC("Runtime"),

    /**
     * 操作指令返回结果的主题
     */
    OPERATION_CALLBACK_TOPIC("Operation/Callback"),

    /**
     * 设备异常时的主题
     */
    EXCEPTION_TOPIC("Exception");

    private final String topic;
}