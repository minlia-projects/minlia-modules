package com.minlia.module.pay.bean;

import com.minlia.module.pay.enums.SysPayChannelEnum;
import com.minlia.module.pay.enums.SysPayMethodEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * <p>
 * 支付-订单
 * </p>
 *
 * @author garen
 * @since 2021-05-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SysPayOrderCro", description = "支付-订单")
public class SysPayOrderCro {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单号")
    @NotBlank
    @Size(max = 32)
    private String orderNo;

    @ApiModelProperty(value = "通道")
    @NotNull
    private SysPayChannelEnum channel;

    @ApiModelProperty(value = "支付方式")
    @NotNull
    private SysPayMethodEnum payMethod;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "总金额")
    @NotNull
    private BigDecimal amount;

    @ApiModelProperty(value = "主题")
    @NotBlank
    @Size(max = 200)
    private String subject;

    @ApiModelProperty(value = "内容")
    @NotBlank
    @Size(max = 200)
    private String body;

}