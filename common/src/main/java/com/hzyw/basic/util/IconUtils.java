package com.hzyw.basic.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author haoyuan
 * date 2019.08.07
 */
@Slf4j
@Component
public class IconUtils {

    public static String buildIconExpress(Integer taskType,List<Integer> value,String strTime){
        String[] split = strTime.split(":");
        String second=split[2].equals("00")? "0":split[2];
        String minute=split[1].equals("00")? "0":split[1];
        String hour=split[0].equals("00")? "0":split[0];
        String iconStart=second+" "+minute+" "+hour+" ";
        String iconEnd="";
        //1是及时，2是每天重复，3是每周，4是每月
        if (taskType==2){
            iconEnd="* * ?";
        }else if(taskType==3){
            //1-6为2-7,7为1
            if(value.contains(7)){
                value.set(value.indexOf(7),0);
            }
            for (int i = 0; i <value.size() ; i++) {
                value.set(i,value.get(i)+1);
            }
            iconEnd="? * "+getStrValue(value);
        }else {
            iconEnd=getStrValue(value)+" * ?";
        }
        return iconStart+iconEnd;
    }

    public static String buildIconEnd(Integer taskType, List<Integer> value) {
        String iconEnd = "";

        if (taskType.intValue() == 2) {
            iconEnd = "* * ?";
        } else if (taskType.intValue() == 3) {

            if (value.contains(Integer.valueOf(7))) {
                value.set(value.indexOf(Integer.valueOf(7)), Integer.valueOf(0));
            }
            for (int i = 0; i < value.size(); i++) {
                value.set(i, Integer.valueOf(((Integer)value.get(i)).intValue() + 1));
            }
            iconEnd = "? * " + getStrValue(value);
        } else {
            iconEnd = getStrValue(value) + " * ?";
        }
        return iconEnd;
    }

    public static String buildIconStart(String strTime) {
        String[] split = strTime.split(":");
        String second = split[2].equals("00") ? "0" : split[2];
        String minute = split[1].equals("00") ? "0" : split[1];
        String hour = split[0].equals("00") ? "0" : split[0];
        return second + " " + minute + " " + hour + " ";
    }

    private static String getStrValue(List<Integer> value){
        StringBuilder strValue=new StringBuilder();
        for (int i = 0; i <value.size() ; i++) {
            strValue=strValue.append(value.get(i)).append(",");
        }
        strValue.deleteCharAt(strValue.lastIndexOf(","));
        return strValue.toString();
    }

//    public static void main(String[] args) {
//        String date="00:00:00";
//        List<Integer> value=new ArrayList();
//        for (int i = 0; i <7 ; i++) {
//            value.add(i+1);
//        }
//        String s = buildIconExpress(3, value, date);
//        System.out.println("icon表达式："+s);
//    }


}

