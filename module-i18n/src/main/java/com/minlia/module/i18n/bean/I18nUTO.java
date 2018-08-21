package com.minlia.module.i18n.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "i18n-update")
public class I18nUTO extends I18nCTO{

    @ApiModelProperty(value = "ID", example = "1")
    @NotNull
    private Long id;

}