package com.minlia.module.bank.bean.qo;

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
@ApiModel("联行号-查询")
public class BankBranchQO extends QueryRequest {

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String district;

    @ApiModelProperty(value = "街道")
    private String street;

    @ApiModelProperty(value = "银行名称")
    private String bankname;

    @ApiModelProperty(value = "支行名称")
    private String branchname;

    @ApiModelProperty(value = "联行号")
    private String number;

}
