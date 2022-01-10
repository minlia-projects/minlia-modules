package com.minlia.module.wallet.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletWithdrawApprovalRo {

    @ApiModelProperty(value = "ID")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "结算金额")
    @NotNull
    @DecimalMin("1")
    private BigDecimal settledAmount;

    @ApiModelProperty(value = "是否通过")
    @NotNull
    private Boolean pass;

    @ApiModelProperty(value = "备注")
    @Size(max = 200)
    private String remark;

}