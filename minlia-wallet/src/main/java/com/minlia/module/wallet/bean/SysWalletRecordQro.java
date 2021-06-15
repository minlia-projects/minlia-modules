package com.minlia.module.wallet.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.module.data.entity.BaseQueryEntity;
import com.minlia.module.wallet.enums.WalletOperationTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class SysWalletRecordQro extends BaseQueryEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "GUID")
    @JsonIgnore
    private Long uid;

    @ApiModelProperty(value = "钱包ID")
    @JsonIgnore
    private Long walletId;

    @ApiModelProperty(value = "类型")
    private WalletOperationTypeEnum type;

    @ApiModelProperty(value = "业务ID")
    private Long businessId;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "备注")
    private String remark;

}