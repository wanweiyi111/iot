package com.hzyw.basic.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Data
@ApiModel(value = "能量报表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class PowerVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column
    @ApiModelProperty(value = "电能量")
    private Double electricEnergy;

    @Column
    @ApiModelProperty(value = "设备code")
    private String equipmentCode;

    @Column
    @ApiModelProperty(value = "节能量")
    private Double saveEnergy;

    @Column
    @ApiModelProperty(value = "设备name")
    private String equipmentName;


}
