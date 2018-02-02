package com.minlia.module.country.body;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by garen on 2017/7/20.
 */
@Data
@ApiModel(value = "国家-修改")
public class CountryUpdateRequestBody extends CountryRequestBody {

    @ApiModelProperty(value = "ID")
    @NotNull
    private Long id;

}
