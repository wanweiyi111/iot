package com.hzyw.basic.dos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "au_role_t")
@Data
@ApiModel(value = "角色表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
/**
 * 角色
 * @author
 * @date 2019.9.17
 */

public class RoleDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**角色ID*/
    @Id
    @Column(name = "ROLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键ID")
    private Long roleId;

    /**角色名称*/
    @Column(name = "ROLE_NAME")
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**应用ID*/
    @Column(name = "APP_ID")
    @ApiModelProperty(value = "应用ID")
    private String appId;

    /**角色描述*/
    @Column(name = "REMARK")
    @ApiModelProperty(value = "角色描述")
    private String remark;

    /**创建时间*/
    @Column(name = "CREATE_TIME")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**修改时间*/
    @Column(name = "MODIFY_TIME")
    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    /**是否启用*/
    @Column(name = "isEnable")
    @ApiModelProperty(value = "是否启用")
    private String isEnable;
}
