package com.minlia.module.country.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by garen on 2017/7/20.
 */
@Data
@ApiModel(value = "国家-修改")
public class CountryURO extends CountryCRO {

    @ApiModelProperty(value = "ID")
    @NotNull
    private Long id;

}
