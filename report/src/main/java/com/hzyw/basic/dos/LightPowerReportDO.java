package com.hzyw.basic.dos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "power_report_t")
@Data
@ApiModel(value = "报表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
/**
 * 大屏项目信息表
 * @author hao yuan
 * @date 2019.08.07
 */
public class LightPowerReportDO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键ID")
    private Long PowerReportId;

    @Column
    @ApiModelProperty(value = "唯一标示")
    private String signCode;


    @Column
    @ApiModelProperty(value = "电能量")
    private Double electricEnergy;

    @Column
    @ApiModelProperty(value = "节能量")
    private Double saveEnergy;

    @Column
    @ApiModelProperty(value = "统计时间")
    private Date reportDate;

    @Column
    @ApiModelProperty(value = "统计类型（light_month,light_day,light_year）")
    private String reportType;

    @Column
    @ApiModelProperty(value = "报表维度（light,pole,project）")
    private String reportDimension;

    @Column
    @ApiModelProperty(value = "设备名称")
    private String equipmentName;


}