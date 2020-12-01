package com.hzyw.basic.dos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "au_login_log_t")
@ApiModel(value = "登陆日志表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Validated
@Data
public class LoginLogDO {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @Column(name = "USERNAME")
    @ApiModelProperty(value = "用户名")
    private String userName;

    @Column(name = "LOGIN_TIME")
    @ApiModelProperty(value = "登录时间")
    private Date loginTime;

    @Column(name = "LOCATION")
    @ApiModelProperty(value = "登录地点")
    private String location;

    @Column(name = "IP")
    @ApiModelProperty(value = "IP地址")
    private String ip;
}
