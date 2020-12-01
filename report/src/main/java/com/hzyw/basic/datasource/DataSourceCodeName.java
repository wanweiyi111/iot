package com.hzyw.basic.datasource;

/**
 1        智慧照明        灯控器
 2        氛围照明        光源模组
 3        智慧WiFi        AP（无线访问接入点）
 4        环境监测        环境传感器
 5        智慧雷达        雷达传感器
 6        应急呼叫        一键式报警盒
 7        5G微基站        5G微基站
 8        信息发布        LED显示屏
 9        公共广播        IP音柱
 10        安防监控        数字摄像机
 11        智能空开        智能空气开关
 12        浸水检测        水浸传感器
 13        充电桩          智能充电桩
 14        智能柜锁        智能柜锁设备
 15        手机USB充电        手机USB充电设备
 16        边缘计算网关        边缘计算网关
 */
public enum DataSourceCodeName {
    UNIONS(100, "unions"),
    LIGHT(4112, "智慧照明"),
    SCREEN(4144, "信息发布"),
    CAMERA(4128, "安防监控"),
    RADAR(4192, "智慧雷达"),
    SENSOR(4176, "环境监测"),
    WIFIN(4129, "智慧WiFi"),
    lock(4208, "智能柜锁"),
    alarm(4240, "紧急呼叫"),
    horn(4272, "公共广播"),
    CHARGER(8193, "充电桩"),
    ;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private int code;
    private String name;

    DataSourceCodeName(int code, String name) {
        this.code = code;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
