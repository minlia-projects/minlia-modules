package com.minlia.module.pay.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.pay.enums.SysPayChannelEnum;
import com.minlia.module.pay.enums.SysPayStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * <p>
 * 支付-订单
 * </p>
 *
 * @author garen
 * @since 2021-05-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_pay_order")
@ApiModel(value = "SysPayOrderEntity对象", description = "支付-订单")
public class SysPayOrderEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单号")
    @TableField("order_no")
    private String orderNo;

    @ApiModelProperty(value = "交易号")
    @TableField("trade_no")
    private String tradeNo;

    @ApiModelProperty(value = "通道")
    @TableField("channel")
    private SysPayChannelEnum channel;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private SysPayStatusEnum status;

    @ApiModelProperty(value = "币种")
    @TableField("currency")
    private String currency;

    @ApiModelProperty(value = "总金额")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty(value = "主题")
    @TableField("subject")
    private String subject;

    @ApiModelProperty(value = "内容")
    @TableField("body")
    private String body;

    @ApiModelProperty(value = "是否删除")
    @TableField("del_flag")
    @TableLogic
    private Boolean delFlag;

}