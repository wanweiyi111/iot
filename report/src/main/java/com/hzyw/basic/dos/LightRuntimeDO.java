package com.hzyw.basic.dos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hzyw.basic.domain.DateDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

@Data
@ApiModel(value = "路灯运行数据信息")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class LightRuntimeDO extends DateDO {
    /**
     *
     */
    private static final long serialVersionUID = 5253272200590726678L;


    @Column
    @ApiModelProperty(value = "设备code")
    private String equipmentCode;

    @Column
    @ApiModelProperty(value = "设备类型")
    private Integer equipmentType;

    @Column
    @ApiModelProperty(value = "设备状态（“1”为正常，“0”为故障）")
    private String equipmentStatus;

    @Column
    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @Column
    @ApiModelProperty(value = "照明主键id")
    private Long lightId;

    @Column
    @ApiModelProperty(value = "电流(单位mA)")
    private Double current;

    @Column
    @ApiModelProperty(value = "电压(单位mV)")
    private Double voltage;

    @Column
    @ApiModelProperty(value = "有功功率(单位w)")
    private Double activePower;

    @Column
    @ApiModelProperty(value = "无功功率(单位w)")
    private Double reactivePower;

    @Column
    @ApiModelProperty(value = "功率因素")
    private Double powerFactor;

    @Column
    @ApiModelProperty(value = "视在功率(单位w)")
    private Double apparentPower;

    @Column
    @ApiModelProperty(value = "频率")
    private Double frequency;

    @Column
    @ApiModelProperty(value = "温度（单位°C）")
    private Double temperature;

    @Column
    @ApiModelProperty(value = "亮度(单位cd/平方米)")
    private Double luminance;

    @Column
    @ApiModelProperty(value = "有功电能（单位kW·h）")
    private Double activeElectricEnergy;

    @Column
    @ApiModelProperty(value = "无功电能（单位kW·h）")
    private Double reactiveElectricEnergy;

    @Column
    @ApiModelProperty(value = "视在电能（单位kW·h）")
    private Double apparentpowerElectricEnergy;

    @Column
    @ApiModelProperty(value = "运行时间（单位秒）")
    private Long runDuration;

    @Column
    @ApiModelProperty(value = "上报时间（单位hh:mm:ss")
    private Long reportingTime = 0L;

    @Column
    @ApiModelProperty(value = "亮度")
    private Integer brightness;

    @Column
    @ApiModelProperty(value = "开关状态（“1”为开启，“0”为关闭")
    private String onOff;

    @Column
    @ApiModelProperty(value = "工作时长")
    private Long workTime;

    @Column
    @ApiModelProperty(value = "额定功率")
    private Integer ratedPower;

    @Column
    @ApiModelProperty(value = "设备名称")
    private String equipmentName;

}
