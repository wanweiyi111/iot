package com.hzyw.basic.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Set;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Data
@ApiModel(value = "能量报表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ProjectPowerVO extends PowerVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column
    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @Column
    @ApiModelProperty(value = "项目id")
    private Set<String> projectCodes;



}
