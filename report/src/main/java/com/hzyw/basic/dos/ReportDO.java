package com.hzyw.basic.dos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "report_info_t")
@Data
@ApiModel(value = "报表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
/**
 * 大屏项目信息表
 * @author hao yuan
 * @date 2019.08.07
 */
public class ReportDO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键ID")
    private Long reportId;

    @Column
    @ApiModelProperty(value = "设备类型")
    private Integer equipmentType;

    @Column
    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @Column(columnDefinition = "double(10,3) default '0.000'")
    @ApiModelProperty(value = "亮灯率")
    private Double LightingRate;

    @Column(columnDefinition = "double(10,3) default '0.000'")
    @ApiModelProperty(value = "故障率")
    private Double failureRate;

    @Column
    @ApiModelProperty(value = "节能量")
    private Double energySaving;

    @Column
    @ApiModelProperty(value = "正常数量")
    private Integer normalCount;

    @Column
    @ApiModelProperty(value = "故障数量")
    private Integer failureCount;

    @Column
    @ApiModelProperty(value = "总计数量")
    private Integer projectTotal;

    @Column
    @ApiModelProperty(value = "统计时间")
    private Date reportDate;

    @Column
    @ApiModelProperty(value = "统计类型（light_month,light_day,light_year,radar_day）")
    private String reportType;


}