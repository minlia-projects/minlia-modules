package com.minlia.hsjs.integral.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 积分-配置
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
@Data
@ApiModel(value = "HsjsIntegralConfigUro")
public class HsjsIntegralConfigUro extends HsjsIntegralConfigCro {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull
    private Long id;

}