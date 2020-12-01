package com.hzyw.basic.dos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "au_permission_t")
@Data
@ApiModel(value = "权限表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
/**
 * 权限
 * @author
 * @date 2019.9.17
 */
public class PermissionDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PERMISSION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @Column(name = "PARENT_ID")
    @ApiModelProperty(value = "上级菜单ID")
    private Long parentId;

    @Column(name = "PERMISSION_NAME")
    @ApiModelProperty(value = "菜单/按钮名称")
    private String permissionName;

    @ApiModelProperty(value = "对应页面")
    private String menuName;

    @Column(name = "PATH")
    @ApiModelProperty(value = "菜单路径")
    private String path;

    @Column(name = "LEVEL")
    @ApiModelProperty(value = "菜单等级")
    private String level;

    @Column(name = "PERMS")
    @ApiModelProperty(value = "权限标识")
    private String  perms;

    @Column(name = "ICON")
    @ApiModelProperty(value = "图标名称")
    private String  icon;


    @Column(name = "APPID")
    @ApiModelProperty(value = "应用ID")
    private Long appId;

    @Column(name = "TYPE")
    @ApiModelProperty(value = "类型 0菜单 1按钮 2子页面")
    private String  type;

    @Column(name = "ORDER_NUM")
    @ApiModelProperty(value = "排序")
    private Long orderNum;


    @Column(name = "ISEnable")
    @ApiModelProperty(value = "是否启用")
    private String  iSEnable;

    @Column(name = "CREATE_TIME")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @Column(name = "MODIFY_TIME")
    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;
}
