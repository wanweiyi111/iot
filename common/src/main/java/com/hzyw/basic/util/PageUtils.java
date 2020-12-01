package com.hzyw.basic.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageUtils {
    public  static Integer getPage(Integer page){
        return  (page == null || page == 0 ? 1 : page);
    }

    public static Integer getPerPage(Integer perPage){
        return  (perPage == null || perPage == 0 ? 10 : perPage);
    }

}