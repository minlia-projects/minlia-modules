package com.minlia.module.currency.bean;

import com.minlia.module.data.bean.QueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 货币
 * </p>
 *
 * @author garen
 * @since 2021-01-08
 */
@Data
@ApiModel(value = "SysCurrencyQro", description = "货币")
public class SysCurrencyQro extends QueryRequest {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "符号")
    private String symbol;

    @ApiModelProperty(value = "是否禁用")
    private Boolean disFlag;

    @ApiModelProperty(value = "是否删除")
    private Boolean delFlag;

}