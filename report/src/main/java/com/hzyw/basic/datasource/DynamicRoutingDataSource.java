package com.hzyw.basic.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        Integer logCodeMsg=DynamicDataSourceContextHolder.get()==null?0:DynamicDataSourceContextHolder.get().getCode();
        String logNameMsg=DynamicDataSourceContextHolder.get()==null?"":DynamicDataSourceContextHolder.get().getName();
        log.info("当前数据源：{}" ,"**** code: "+logCodeMsg+", name: "+logNameMsg);
        return DynamicDataSourceContextHolder.get();
    }
}
