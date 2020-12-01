package com.hzyw.basic.dos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "sensor_report_t")
@Data
@ApiModel(value = "报表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
/**
 * 大屏项目信息表
 * @author hao yuan
 * @date 2019.08.07
 */
public class SensorReportDO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键ID")
    private Long sensorReportId;

    @Column
    @ApiModelProperty(value = "唯一标示")
    private String signCode;

    @Column
    @ApiModelProperty(value = "PM2.5，单位ug/m3")
    private Float pm25;

    @Column
    @ApiModelProperty(value = "PM10，单位ug/m3")
    private Float pm10;

    @Column
    @ApiModelProperty(value = "噪声, 单位dB(A)")
    private Float noise;

    @Column
    @ApiModelProperty(value = "温度, 单位°C")
    private Float temperature;

    @Column
    @ApiModelProperty(value = "湿度, 单位RH")
    private Float humidity;

    @Column
    @ApiModelProperty(value = "风向, 单位°")
    private Float windDirection;

    @Column
    @ApiModelProperty(value = "风速, 单位m/s")
    private Float windSpeed;

    @Column
    @ApiModelProperty(value = "气压, 单位hPa")
    private Float pressure;

    @Column
    @ApiModelProperty(value = "雨量, 单位mm")
    private Float rainfall;
    @Column
    @ApiModelProperty(value = "统计时间")
    private Date reportDate;

    @Column
    @ApiModelProperty(value = "统计类型（light_month,light_day,light_year）")
    private String reportType;

    @Column
    @ApiModelProperty(value = "报表维度")
    private String reportDimension;

    @Column
    @ApiModelProperty(value = "设备名称")
    private String equipmentName;


}