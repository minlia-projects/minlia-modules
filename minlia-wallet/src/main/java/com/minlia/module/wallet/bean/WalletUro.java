package com.minlia.module.wallet.bean;

import com.minlia.module.wallet.enums.WalletOperationTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletUro {

    @ApiModelProperty(value = "UID")
    private Long uid;

    @ApiModelProperty(value = "类型")
    private WalletOperationTypeEnum type;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "业务类型")
    private String businessType;

    @ApiModelProperty(value = "业务ID")
    private String businessId;

    private String remark;

}