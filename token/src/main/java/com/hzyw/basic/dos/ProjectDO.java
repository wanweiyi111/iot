package com.hzyw.basic.dos;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel(value = "项目表")
/**
 * 项目表
 * @author
 * @date 2019.9.17
 */
public class ProjectDO implements Serializable{


    private Long id;

    private String projectName;

    private String city;

    private String district;

    private String street;

    private Double longitude;      //项目经度

    private Double latitude;   //项目纬度

    private Date createTime;

    private Date modifyTime;
}
