package com.hzyw.basic.dos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "au_app_t")
@Data
@ApiModel(value = "应用表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
/**
 * 权限应用
 * @author
 * @date 2019.9.17
 */
public class AuAppDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column( name = "id" )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @Column(name = "name")
    @ApiModelProperty(value = "应用名称")
    private String name;

    @Column(name = "code")
    @ApiModelProperty(value = "编码")
    private String code;

    @Column(name = "sort")
    @ApiModelProperty(value = "排序")
    private Integer sort;

    @Column(name = "createTime")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @Column(name = "isEnable")
    @ApiModelProperty(value = "是否启用")
    private String isEnable;
}
