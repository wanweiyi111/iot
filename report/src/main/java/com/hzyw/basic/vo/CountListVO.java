package com.hzyw.basic.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Data
@Slf4j
public class CountListVO implements Serializable {

    @ApiModelProperty(value = "亮度率")
    private List<String> brightnessRate;

    @ApiModelProperty(value = "日期")
    private List<String> brightnessDate;

    @ApiModelProperty(value = "节能量")
    private List<String> energySaving;

    @ApiModelProperty(value = "日期")
    private List<String> energySavingDate;

    @ApiModelProperty(value = "故障率")
    private List<String> errorRate;

    @ApiModelProperty(value = "日期")
    private List<String> errorDate;

    @ApiModelProperty(value = "电能")
    private List<String> electricEnergy;

    @ApiModelProperty(value = "日期")
    private List<String> electricDate;


}
