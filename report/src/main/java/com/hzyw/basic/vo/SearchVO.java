package com.hzyw.basic.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Data
@ApiModel(value = "查询入参信息")
public class SearchVO implements Serializable {

    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @ApiModelProperty(value = "搜索类型,'1'周，'2'月，'3'年")
    private Integer searchType;

    @ApiModelProperty(value = "搜索日期")
    private List<Date> dates;
}
