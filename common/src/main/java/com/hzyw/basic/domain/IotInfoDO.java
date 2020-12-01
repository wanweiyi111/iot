package com.hzyw.basic.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.dozer.Mapping;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author hao yuan
 */

@MappedSuperclass
@Data
public class IotInfoDO extends DateDO{

    @Mapping("deviceId")
    @Column
    @ApiModelProperty(value="设备编号")
    private String equipmentCode;

    @Column
    @ApiModelProperty(value="设备名称")
    private String equipmentName;

    @Column
    @ApiModelProperty(value="出产批次号")
    private String productLotNumber;

    @Column
    @ApiModelProperty(value="有效期")
    private Integer guaranteePeriod;

    @Column
    @ApiModelProperty(value="出厂日期")
    private Date productionDate;

    @Column
    @ApiModelProperty(value="设备型号")
    private String equipmentModel;

    @Column
    @ApiModelProperty(value="支持的硬件接口")
    private String hardwareInterface;

    @Column
    @ApiModelProperty(value="支持的协议信息")
    private String protocolInformation;

    @Column
    @ApiModelProperty(value="软件规格")
    private String softwareSpecification;

    @Column
    @ApiModelProperty(value="硬件规格")
    private String hardwareSpecification;

    @Column
    @ApiModelProperty(value="生产商名称")
    private String manufacturerName;

    @Column
    @ApiModelProperty(value="生产商编号")
    private String manufacturerCode;

    @Column
    @ApiModelProperty(value="生产商责任人")
    private String manufacturerPerson;

    @Column
    @ApiModelProperty(value="生产商责任人电话")
    private String manufacturerPhone;

    @Column
    @ApiModelProperty(value="生产商责任人邮箱")
    private String manufacturerEmail;

    //TODO 获取token方法待完善
    @SuppressWarnings("all")
    @Column
    @ApiModelProperty(value="生产商地址")
    private String manufacturerAdress;

    @Column
    @ApiModelProperty(value="设备类型")
    private Integer equipmentType;

    @Column
    @ApiModelProperty(value="安装时间")
    private Date installationTime;

    @Column
    @ApiModelProperty(value="安装单位")
    private String installationCompany;

    @Column
    @ApiModelProperty(value="安装人")
    private String installation;

    @Column
    @ApiModelProperty(value="联系方式")
    private String phone;

    @Column
    @ApiModelProperty(value="灯杆名称")
    private String poleName;

    @Column
    @ApiModelProperty(value="灯杆编号")
    private String poleCode;

    @Column
    @ApiModelProperty(value="安装位置")
    private String location;

    @Column(columnDefinition = "double(14,6) default '0.000000'")
    @ApiModelProperty(value = "灯杆经度")
    private Double longitude;

    @Column(columnDefinition = "double(14,6) default '0.000000'")
    @ApiModelProperty(value = "灯杆纬度")
    private Double latitude;

    @Column
    @ApiModelProperty(value="灯杆型号")
    private String poleModel;

    @Column
    @ApiModelProperty(value="设备IP")
    private String equipmentIp;

    @Column
    @ApiModelProperty(value="设备MAC")
    private String equipmentMac;

    @Column
    @ApiModelProperty(value="网关信息")
    private String gatewayInformation;

    @Column
    @ApiModelProperty(value="灯杆信息")
    private String lightPoleInformation;

    @Column
    @Mapping("networkState")
    @ApiModelProperty(value = "联网状态(“1”为在线，“0”为离线)")
    private Integer networkState;

    @Column
    @ApiModelProperty(value="入网时间")
    private Date onlineTime;

    @Column
    @ApiModelProperty(value="离网时间")
    private Date offlineTime;

    @Column
    @Mapping("equipmentStatus")
    @ApiModelProperty(value = "设备状态（“1”为正常，“0”为故障）")
    private Integer equipmentStatus;

    @Mapping("on_off")
    @Column
    @ApiModelProperty(value = "开关状态（“1”为开启，“0”为关闭）")
    private String onOff;

    @Column
    @ApiModelProperty(value="超时时间")
    private Long timeoutPeriod;

    @Column
    @ApiModelProperty(value="项目信息id")
    private Long projectId;

    @Column
    @ApiModelProperty("设备编码-用户配置")
    private String equipmentNumber;

}
