package com.minlia.module.bankcard.ro;

import com.minlia.module.bankcard.enumeration.BankCardType;
import com.minlia.module.common.validation.IdCard;
import com.minlia.module.data.bean.QueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("银行卡-查询")
public class BankCardQRO extends QueryRequest {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "GUID")
    private String guid;

    @ApiModelProperty(value = "类型")
    private BankCardType type;

    @ApiModelProperty(value = "持卡人")
    private String holder;

    @ApiModelProperty(value = "银行卡号")
    @IdCard
    private String number;

    @ApiModelProperty(value = "联行号")
    private String bankCode;

    @ApiModelProperty(value = "银行名称")
    private String bankName;

    @ApiModelProperty(value = "是否提现卡")
    private Boolean isWithdraw;

}
