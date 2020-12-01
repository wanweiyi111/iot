package com.hzyw.basic.datasource;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
public enum DataSourceKey {
    DB_UNIONS(100, "unions"),
    DB_LIGHT(4112, "light"),
    DB_SCREEN(4144, "screen"),
    DB_CAMERA(4128, "camera"),
    DB_WIFI(4160, "wifi"),
    DB_POLE(1, "pole"),
    DB_SENSOR(4176, "sensor"),
    DB_RADAR(4192, "radar"),
    DB_CHARGER(8, "charger"),
    DB_REPORT(9, "report"),
    ;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private int code;
    private String name;

    DataSourceKey(int code, String name) {
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
