package com.minlia.module.wallet.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.wallet.enums.WalletOperationTypeEnum;
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
 * 钱包-记录
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
@TableName("sys_wallet_record")
@ApiModel(value = "SysWalletRecordEntity对象", description = "钱包-记录")
public class SysWalletRecordEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "GUID")
    @TableField("uid")
    private Long uid;

    @ApiModelProperty(value = "钱包ID")
    @TableField("wallet_id")
    private Long walletId;

    @ApiModelProperty(value = "类型")
    @TableField("type")
    private WalletOperationTypeEnum type;

    @ApiModelProperty(value = "金额")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty(value = "业务类型")
    @TableField("business_type")
    private String businessType;

    @ApiModelProperty(value = "业务ID")
    @TableField("business_id")
    private String businessId;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

}