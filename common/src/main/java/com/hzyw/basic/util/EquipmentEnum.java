package com.hzyw.basic.util;

import com.hzyw.basic.mqtt.IotTopicEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Getter
@AllArgsConstructor
public enum EquipmentEnum {
    /**
     * 设备大类
     * 16	IoT设备	DEVICE
     * 4096	盒子	HZYW_BOX
     * 4112	灯控	LAMP
     * 4128	摄像头	CAMERA
     * 4129	公共WIFI	WIFI
     * 4144	大屏	SCREEN
     * 4176	环境传感器	ENV_SENSOR
     * 4192	雷达	RADAR
     * 4208	智能锁	LOCK
     * 4240	一键报警	ALARM_BOX
     * 4272	广播	BROADCAST
     * 8192	PLC集中控制器	PLC_BOX
     * 8193	充电桩	CHARGING_PILE
     * 65280	灯杆	POLE
     */
    DEVICE("IoT设备", 4096,"iot"),
    POLE("灯杆", 1,"pole"),
    EDGE_BOX("盒子", 2,"edge_box"),
    LAMP("路灯", 4112,"light"),
    SCREEN("大屏", 4144,"screen"),
    CAMERA("摄像头", 4128,"camera"),
    WIFI("公共WIFI", 4160,"wifi"),
    ENV_SENSOR("环境传感器", 4176,"sensor"),
    RADAR("雷达", 4192,"radar"),
    LOCK("智能锁", 4208,"lock"),
    ALARM("一鍵求助",4240,"alarm"),
    HORN("喇叭", 4272,"horn"),
    CHARGING_PILE("充电桩", 8193,"charger"),
    SOS_ALARM("一键呼叫", 10,"sos_alarm"),
    WATER_GAUGE("智能水尺", 11,"water_gauge"),
    FLOOD_SENSOR("水浸传感器", 12,"flood_semsor"),
    SEWER_COVER("智能井盖", 13,"sewer_cover"),

    ALL("所有设备", 0,"all");
    private final String description;
    private final Integer index;
    private final String name;

    public final String getEquipmentInfoTopic() {
        return IotTopicEnum.EQUIPMENT_INFO_TOPIC.getTopic() + "/" + (this.index != 0 ? this.index : "+");
    }

    public final String getRuntimeTopic() {
        return IotTopicEnum.RUNTIME_TOPIC.getTopic() + "/" + (this.index != 0 ? this.index : "+");
    }

    public final String getExceptionTopic() {
        return IotTopicEnum.EXCEPTION_TOPIC.getTopic() + "/" + (this.index != 0 ? this.index : "+");
    }

    public final String getOperationCallbackTopic() {
        return IotTopicEnum.OPERATION_CALLBACK_TOPIC.getTopic();
    }

    public static EquipmentEnum getEquipmentEnum(Integer index){
        EquipmentEnum[] enumConstants = EquipmentEnum.class.getEnumConstants();
        for (EquipmentEnum equipment:enumConstants ) {
            if ((equipment.getIndex()).equals(index)){
               return equipment;
           }
        }
        return null;
    }
    public static EquipmentEnum getEquipmentEnum(String name){
        EquipmentEnum[] enumConstants = EquipmentEnum.class.getEnumConstants();
        for (EquipmentEnum equipment:enumConstants ) {
            if ((equipment.getName()).equals(name)){
                return equipment;
            }
        }
        return null;
    }
}