package com.hzyw.basic.util;

public interface Constant {

    interface PageConstant {
        //页面编号
        int defaultPageNum = 1;
        //每页记录条数
        int defaultPageSize = 10;
    }

    interface MqttRec {
        String excTopic = "screenException";
        String runTopic = "screenRuntime";
        String infoTopic = "screenInfo";

    }

    interface Status {
        //在网状态("1"为在线，“0”为离线)
        String onNet = "1";
        String offNet = "0";
        //设备状态（“1”为开启，“0”为故障）
        String onWork = "1";
        String errorWork = "0";
    }

    interface ReportType {
        String lightDay = "light_day";
        String Day = "day";
        String Month = "month";
        String Year = "year";
        String Week = "week";
    }
    interface ReportDimension {
        String light = "light";
        String pole = "pole";
        String project = "project";
    }
    interface Dimension {
        String equipment = "equipment";
        String pole = "pole";
        String project = "project";
    }

}
