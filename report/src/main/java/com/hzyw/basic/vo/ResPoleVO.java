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
public class ResPoleVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column
    @ApiModelProperty(value = "灯杆id")
    private Long poleId;

    @Column
    @ApiModelProperty(value = "设备名称")
    private String equipmentName;

    @Column
    @ApiModelProperty(value = "电能列表")
    private List<Double> energyList;

    @Column
    @ApiModelProperty(value = "节能列表")
    private List<Double> saveList;






}
