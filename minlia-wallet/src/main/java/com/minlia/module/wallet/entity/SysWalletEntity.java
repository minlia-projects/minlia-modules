package com.minlia.module.wallet.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
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
 * 钱包
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
@TableName("sys_wallet")
@ApiModel(value = "SysWalletEntity对象", description = "钱包")
public class SysWalletEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "GUID")
    @TableField("uid")
    private Long uid;

    @ApiModelProperty(value = "总金额")
    @TableField("total")
    private BigDecimal total;

    @ApiModelProperty(value = "余额")
    @TableField("balance")
    private BigDecimal balance;

    @ApiModelProperty(value = "冻结金额")
    @TableField("frozen")
    private BigDecimal frozen;

    @ApiModelProperty(value = "提现金额")
    @TableField("withdraw")
    private BigDecimal withdraw;

    @ApiModelProperty(value = "流水")
    @TableField("flow")
    private BigDecimal flow;

}