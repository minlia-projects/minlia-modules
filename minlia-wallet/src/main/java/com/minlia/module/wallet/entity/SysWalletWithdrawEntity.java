package com.minlia.module.wallet.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.wallet.enums.WithdrawStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * <p>
 * 钱包-提现
 * </p>
 *
 * @author garen
 * @since 2021-05-10
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_wallet_withdraw")
@ApiModel(value = "SysWalletWithdrawEntity对象", description = "钱包-提现")
public class SysWalletWithdrawEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "GUID")
    @TableField("uid")
    private Long uid;

    @ApiModelProperty(value = "钱包ID")
    @TableField("wallet_id")
    private Long walletId;

    @ApiModelProperty(value = "通道")
    @TableField("channel")
    private String channel;

    @ApiModelProperty(value = "金额")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty(value = "收款人")
    @TableField("payee")
    private String payee;

    @ApiModelProperty(value = "账号")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private WithdrawStatusEnum status;

}