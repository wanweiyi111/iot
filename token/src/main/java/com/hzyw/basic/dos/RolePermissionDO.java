package com.hzyw.basic.dos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "au_role_permission_t")
@Data
@ApiModel(value = "权限角色关系表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
/**
 * 权限角色关系
 * @author
 * @date 2019.9.17
 */

public class RolePermissionDO {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @Column(name = "ROLE_ID")
    @ApiModelProperty(value = "角色ID")
    private Long ROLEID;

    @Column(name = "PERMISSION_ID")
    @ApiModelProperty(value = "权限ID")
    private Long permissionId;

    @Column(name = "appId")
    @ApiModelProperty(value = "appId")
    private Long appId;
}
