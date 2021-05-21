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
 * 钱包-转账
 * </p>
 *
 * @author garen
 * @since 2021-05-12
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_wallet_transfer")
@ApiModel(value = "SysWalletTransferEntity对象", description = "钱包-转账")
public class SysWalletTransferEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "FROM")
    @TableField("`from`")
    private Long from;

    @ApiModelProperty(value = "TO")
    @TableField("`to`")
    private Long to;

    @ApiModelProperty(value = "金额")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty(value = "业务类型")
    @TableField("business_type")
    private String businessType;

    @ApiModelProperty(value = "业务ID")
    @TableField("business_id")
    private Long businessId;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

}