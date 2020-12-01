package com.hzyw.basic.dos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "au_user_t")
@Data
@ApiModel(value = "用户表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
//@Validated
/**
 * 用户
 * @author
 * @date 2019.9.17
 */
public class UserDO implements Serializable{

    private static final long serialVersionUID = 1L;

    /**用户ID*/
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "用户ID")
    private Long id;

    /**用户名*/
    @Column(name = "USERNAME")
    @ApiModelProperty(value = "用户名")
    //@NotBlank(message = "用户名不能为空")
    private String userName;

    /**密码*/
    @Column(name = "PASSWORD")
    @ApiModelProperty(value = "密码")
    //@NotBlank(message = "密码不能为空")
    private String passWord;

    /**部门ID*/
    @Column(name = "DEPT_ID")
    @ApiModelProperty(value = "部门ID")
    //@NotNull(message = "关联部门不能为空")
    private Integer deptId;


    /**项目ID*/
    @Column(name = "PROJECT_ID")
    @ApiModelProperty(value = "项目ID")
   // @NotNull(message = "关联项目不能为空")
    private Integer projectId;

    /**邮箱*/
    @Column(name = "EMAIL")
    @ApiModelProperty(value = "邮箱")
    //@Email(message = "请填写正确的邮箱地址!")
    private String email;

    /**联系电话*/
    @Column(name = "MOBILE")
    @ApiModelProperty(value = "联系电话")
    private String mobile;

    /**身份证号码*/
//    @Column(name = "ID_NUMBER")
//    @ApiModelProperty(value = "身份证号码")
//    private String idNumber;

    /**车牌号*/
    @Column(name = "CAR_NUMBER")
    @ApiModelProperty(value = "车牌号")
    private String carNumber;

    /**工号*/
    @Column(name = "JOB_NUMBER")
    @ApiModelProperty(value = "工号")
    private String jobNumber;


    /**用户类型*/
    @Column(name = "USER_TYPE")
    @ApiModelProperty(value = "用户类型")
    private String userType;
    
    /**状态 0锁定 1有效2无效*/
    @Column(name = "STATUS")
    @ApiModelProperty(value = "状态 0锁定 1有效2无效")
    private String status;

    /**最近访问时间*/
    @Column(name = "LAST_LOGIN_TIME")
    @ApiModelProperty(value = "最近访问时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    /**性别 0男 1女 2保密*/
    @Column(name = "SSEX")
    @ApiModelProperty(value = "性别 0男 1女 2保密")
    private String ssex;

    /**描述*/
    @Column(name = "DESCRIPTION")
    @ApiModelProperty(value = "描述")
    private String description;

    /**用户头像*/
    @Column(name = "AVATAR")
    @ApiModelProperty(value = "用户头像")
    private String avatar;

    /**创建时间*/
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**修改时间*/
    @Column(name = "MODIFY_TIME")
    private Date modifyTime;

    //有效开始日期
    @Column(name = "start_effective_period")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date startEffectivePeriod;

    /**有效结束日期*/
    @Column(name = "end_effective_period")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date endEffectivePeriod;

    /**真实姓名*/
    @Column(name = "REALNAME")
    private String realName;

    // 默认密码
    public static final String DEFAULT_PASSWORD = "Hzyw@123";
    

    /**
     * 账户状态
     */
    public static final String STATUS_VALID = "1";

    public static final String STATUS_LOCK = "0";

    //private List<ProjectDO> projectDOList;

}
