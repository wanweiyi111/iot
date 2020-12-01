package com.hzyw.basic.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Data
@ApiModel(value = "能量查询VO")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class SearchLightPowerVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "查询类型year,month,week")
    private String searchType;

    @Column
    @ApiModelProperty(value = "查询维度pole,project")
    private String searchDimension;

    @Column
    @ApiModelProperty(value = "查询日期")
    private List<Date> searchDate;

    @Column
    @ApiModelProperty(value = "项目id")
    private String projectId;



}
