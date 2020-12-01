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
@ApiModel(value = "能量报表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ResSensorVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column
    @ApiModelProperty(value = "xAxis")
    private List<String> dates;

    @Column
    @ApiModelProperty(value = "yAxis")
    private List<ResSensorReportVO> data;

    @Column
    @ApiModelProperty(value = "yAxis-charge")
    private List<ResChargeReportVO> dataCharge;






}
