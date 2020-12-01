package com.hzyw.basic.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hzyw.basic.domain.DateDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Data
@ApiModel(value = "灯杆设备绑定表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class PoleEquipmentVO extends DateDO {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键ID")
    private Long id;

    @Column
    @ApiModelProperty(value = "主键id")
    private Long equipmentId;

    @Column
    @ApiModelProperty(value = "主键id")
    private Long poleId;

    @Column
    @ApiModelProperty(value = "设备类型")
    private Integer equipmentType;

    @Column
    @ApiModelProperty(value = "设备code")
    private String equipmentCode;

    @Column
    @ApiModelProperty(value = "设备名称")
    private String equipmentName;

}
