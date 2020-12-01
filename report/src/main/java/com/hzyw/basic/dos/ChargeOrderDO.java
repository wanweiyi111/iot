package com.hzyw.basic.dos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hzyw.basic.domain.DateDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "充电桩基础信息表")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
/**
 * 充电桩端口数据控制对象
 * @author hao yuan
 * @date 2019.7.29
 */
public class ChargeOrderDO extends DateDO {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     *充电桩序列号	Charging pile Serial Number=chargerId
     * 充电桩口号	Port of Charge=chargePortId
     * 订单号	Order Number
     * 订单类型	Order Type
     * 订单状态	Order Status
     * 充电枪号	Charging gun number
     * 充电开始时间	Begin time of Charging
     * 充电完成时间	End time of Charging
     * 充电电量	Total charge
     * 充电费用	Amount of charge
     * 停车费用	Amount Parking
     * 总共支付费用	Total Amount
     * 支付状态	Payment Status
     * 支付方式	Payment Method
     */
    @ApiModelProperty(value = "充电端口号")
    private String chargePortId;

    @ApiModelProperty(value = "充电桩id")
    private String chargerId;

    @ApiModelProperty(value = "订单号")
    private String orderNumber;

    @ApiModelProperty(value = "订单类型")
    private String orderType;

    @ApiModelProperty(value = "订单状态")
    private String orderStatus;

    @ApiModelProperty(value = "充电枪号")
    private String chargingGunNumber;

    @ApiModelProperty(value = "充电开始时间")
    private Date startTime;

    @ApiModelProperty(value = "充电完成时间")
    private Date endTime;

    @ApiModelProperty(value = "充电电量")
    private String totalCharge;

    @ApiModelProperty(value = "充电费用")
    private String amountOfCharge;


    @ApiModelProperty(value = "停车费用")
    private String amountParking;


    @ApiModelProperty(value = "总共支付费用")
    private String totalAmount;


    @ApiModelProperty(value = "支付状态")
    private String paymentStatus;


    @ApiModelProperty(value = "支付方式")
    private String paymentMethod;

    @ApiModelProperty(value = "电站编号")
    private String stationId;


    @ApiModelProperty(value = "项目编号")
    private Long projectId;


}