package com.hzyw.basic.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Data
@ApiModel(value = "报表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ResChargeReportVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Column
    @ApiModelProperty(value = "唯一标示")
    private String signCode;

    @Column
    @ApiModelProperty(value = "项目ID")
    private String projectId;

    @Column
    @ApiModelProperty(value = "充电次数")
    private List<Integer> totalNumbers;

    @Column
    @ApiModelProperty(value = "充电时间")
    private List<Double> totalTimes;

    @Column
    @ApiModelProperty(value = "充电总量")
    private List<Double> totalPowers;








}
