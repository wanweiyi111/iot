package com.hzyw.basic.dos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "au_dept_t")
@Data
@ApiModel(value = "部门表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
/**
 * 部门
 * @author
 * @date 2019.9.17
 */
public class AuDeptDO implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "DEPT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键ID")
    private Long deptId;

    @Column(name = "PARENT_ID")
    @ApiModelProperty(value = "上级部门ID")
    private String parentId;

    @Column(name = "DEPT_NAME")
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @Column(name = "ORDER_NUM")
    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @Column(name = "CREATE_TIME")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @Column(name = "MODIFY_TIME")
    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

}
