package com.minlia.module.country.bean.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.minlia.module.data.body.QueryRequest;

import javax.validation.constraints.Pattern;

/**
 * Created by garen on 2017/7/20.
 */
@Data
@ApiModel(value = "国家-查询")
public class CountryQO implements QueryRequest {

    @ApiModelProperty(value = "名称",example = "中国")
    private String name;

    @ApiModelProperty(value = "编码",example = "cn")
    @Pattern(regexp = "^[a-z_A-Z]{2,10}$")
    private String code;

    @ApiModelProperty(value = "语言",example = "zh_CN")
    private String language;

}
