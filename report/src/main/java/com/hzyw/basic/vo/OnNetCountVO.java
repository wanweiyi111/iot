package com.hzyw.basic.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Data
@ApiModel(value = "设备在线状态统计")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class OnNetCountVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目信息id")
    private Long projectId;

    @ApiModelProperty(value = "项目设备总数")
    private Integer allTotal;

    @ApiModelProperty(value = "项目设备在线总数")
    private Integer onTotal;

    @ApiModelProperty(value = "项目设备离线总数")
    private Integer offTotal;

    @ApiModelProperty(value = "项目设备故障总数")
    private Integer errorTotal;

    @ApiModelProperty(value = "是否有权限")
    private Boolean hasPermission;

}
