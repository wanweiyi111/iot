package com.hzyw.basic.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.hzyw.basic.domain.DateDTO;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author haoyuan
 * date 2019.08.07
 */
@Slf4j
@Component
public class DateTools {
    private static final int MAX_DAY = 7;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd");

    /**
     * 获取指定日期的本周日期
     * @return  DateDTO 一周的日期列表
     */
    public static DateDTO getTimeInterval(Date date) {
        DateDTO dateDTO = new DateDTO();
        List<String> listDate = new ArrayList<>();
        List<Date> dates = new ArrayList<>();
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        // 获得当前日期是一个星期的第几天
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
//        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
//        if (1 == dayWeek) {
//            cal.add(Calendar.DAY_OF_MONTH, -1);
//        }
//        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
//        cal.setFirstDayOfWeek(Calendar.MONDAY);
//        // 获得当前日期是一个星期的第几天
//        int day = cal.get(Calendar.DAY_OF_WEEK);
//        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
//        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);

        for (int i = 0; i < MAX_DAY; i++) {
            listDate.add(DATE_FORMAT.format(cal.getTime()));
            dates.add(cal.getTime());
            cal.add(Calendar.DATE, -1);
        }
        Collections.reverse(listDate);
        Collections.reverse(dates);
        dateDTO.setDateList(dates);
        dateDTO.setStrList(listDate);
        return dateDTO;
    }

//    public static void main(String[] args) {
//        DateDTO timeInterval = getTimeInterval(new Date());
//        System.out.println(timeInterval.getDateList().toString());
//        System.out.println(timeInterval.getStrList().toString());
//    }

    /**
     * java 获取 获取某年某月 所有日期
     *
     * @param date 指定日期
     * @return DateDTO 指定月份所有日期
     */
    @SuppressWarnings("JavaDoc")
    public static DateDTO getMonthFullDay(Date date) {
        List<String> fullDayList = new ArrayList<>(32);
        DateDTO dateDTO = new DateDTO();
        // 获得当前日期对象
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        List<Date> dates = new ArrayList<>();
//        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
//        // 1月从0开始
//        int month = cal.get(Calendar.MONTH);
//        cal.set(Calendar.MONTH, month);
//        // 当月1号
//        cal.set(Calendar.DAY_OF_MONTH, 1);
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//        for (int j = 1; j <= count; j++) {
////            dates.add(cal.getTime());
////            fullDayList.add(DATE_FORMAT.format(cal.getTime()));
////            cal.add(Calendar.DAY_OF_MONTH, 1);
////        }
        for (int i = 0; i < count; i++) {
            fullDayList.add(DATE_FORMAT.format(cal.getTime()));
            dates.add(cal.getTime());
            cal.add(Calendar.DATE, -1);
        }
        Collections.reverse(fullDayList);
        Collections.reverse(dates);
        dateDTO.setDateList(dates);
        dateDTO.setStrList(fullDayList);
        return dateDTO;
    }

    /**
     * java 获取 获取某年某月
     *
     * @param date 指定日期
     * @return Map 指定年份的所有月份
     */
    public static Map<String, Object> getMonthOfYear(Date date) {
        List<String> fullMonthList = new ArrayList<>(12);
        Map<String, Object> map = new HashMap<>();
        List<Date> dates = new ArrayList<>();
        // 获得当前日期对象
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        for (int j = 1; j <= 12; j++) {
            fullMonthList.add(year+"-"+j);
            cal.set(Calendar.MONTH, j-1);
            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.DAY_OF_MONTH,1);
            Date time = cal.getTime();
            dates.add(time);
        }
        map.put("String", fullMonthList);
        map.put("date", dates);
        return map;
    }

}

