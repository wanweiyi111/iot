package com.hzyw.basic.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Data
@ApiModel(value = "设备信息表")
public class EquipmentVO {

    @ApiModelProperty(value = "设备编号")
    private String equipmentCode;

    @ApiModelProperty(value = "设备名称")
    private String equipmentName;

    @ApiModelProperty(value = "设备类型")
    private Integer equipmentType;

    @ApiModelProperty(value = "在网状态(“0”为在线，“1”为离线)")
    private String networkState;

    @ApiModelProperty(value = "设备状态（“1”为正常,“0”为故障）")
    private String equipmentStatus;

    @ApiModelProperty(value = "项目信息id")
    private Long projectId;
}
