package com.hzyw.basic.dos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "au_log_t")
@Data
@ApiModel(value = "操作日志表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
/**
 * 操作日志
 * @author
 * @date 2019.9.17
 */
public class LogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @Column(name = "USERNAME")
    @ApiModelProperty(value = "操作用户")
    private String userName;

    @Column(name = "OPERATION")
    @ApiModelProperty(value = "操作内容")
    private String operation;

    @Column(name = "TIME")
    @ApiModelProperty(value = "耗时")
    private Integer time;

    @Column(name = "METHOD")
    @ApiModelProperty(value = "操作方法")
    private String  method;

    @Column(name = "PARAMS")
    @ApiModelProperty(value = "方法参数")
    private String  params;

    @Column(name = "IP")
    @ApiModelProperty(value = "操作者IP")
    private String  ip;

    @Column(name = "location")
    @ApiModelProperty(value = "操作地点")
    private String  location;

    @Column(name = "CREATE_TIME")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
