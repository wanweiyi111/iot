package com.hzyw.basic.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author haoyuan
 */
@Data
public class DateDTO {


    private List<String> strList;

    private List<Date> dateList;
}
