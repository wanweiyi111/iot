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
public class ResSensorReportVO implements Serializable {

    /**
     * 按环境监测指标的9个指标展示趋势图
     * 【温度、湿度、PM2.5、PM10、气压、风速、风向、雨量、噪音】，
     * 统计时间维度：周、月、年趋势图
     */
    private static final long serialVersionUID = 1L;
    @Column
    @ApiModelProperty(value = "唯一标示")
    private String signCode;

    @Column
    @ApiModelProperty(value = "温度")
    private List<Float> temperatures;

    @Column
    @ApiModelProperty(value = "湿度")
    private List<Float> humiditys;

    @Column
    @ApiModelProperty(value = "pm25")
    private List<Float> pm25s;

    @Column
    @ApiModelProperty(value = "pm10")
    private List<Float> pm10s;

    @Column
    @ApiModelProperty(value = "气压")
    private List<Float> pressures;

    @Column
    @ApiModelProperty(value = "风速")
    private List<Float> windSpeed;
    @Column
    @ApiModelProperty(value = "风向")
    private List<Float> windDirections;

    @Column
    @ApiModelProperty(value = "雨量")
    private List<Float> rainfall;

    @Column
    @ApiModelProperty(value = "噪音")
    private List<Float> noises;





}
