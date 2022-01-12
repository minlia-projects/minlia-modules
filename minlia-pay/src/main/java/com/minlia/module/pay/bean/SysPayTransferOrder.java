package com.minlia.module.pay.bean;

import com.minlia.module.pay.enums.SysPayChannelEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * <p>
 * 支付-转账
 * </p>
 *
 * @author garen
 * @since 2021-05-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SysPayTransferOrder", description = "支付-转账")
public class SysPayTransferOrder {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "通道")
    @NotNull
    private SysPayChannelEnum channel;

    @ApiModelProperty(value = "订单号")
    @NotBlank
    @Size(max = 64)
    private String orderNo;

    @ApiModelProperty(value = "金额")
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @ApiModelProperty(value = "主题")
    @NotBlank
    @Size(max = 64)
    private String subject;

    @ApiModelProperty(value = "备注")
    @NotBlank
    @Size(max = 200)
    private String remark;

    @ApiModelProperty(value = "收款人名称")
    @NotBlank
    @Size(max = 64)
    private String payeeName;

    @ApiModelProperty(value = "收款人账号")
    @NotBlank
    @Size(max = 64)
    private String payeeAccount;

}