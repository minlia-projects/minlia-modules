package com.minlia.module.country.bean.to;

import com.minlia.module.country.bean.to.CountryTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by garen on 2017/7/20.
 */
@Data
@ApiModel(value = "国家-修改")
public class CountryUTO extends CountryTO {

    @ApiModelProperty(value = "ID")
    @NotNull
    private Long id;

}
