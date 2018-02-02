package com.minlia.module.country.body;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by garen on 2017/7/20.
 */
@Data
@ApiModel(value = "国家-创建")
public class CountryRequestBody {

    @ApiModelProperty(value = "名称",example = "中国")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "编码",example = "cn")
    @NotBlank
    @Pattern(regexp = "^[a-z_A-Z]{2,10}$")
    private String code;

    @ApiModelProperty(value = "语言",example = "zh_CN")
    @NotNull
    private String language;

    @ApiModelProperty(value = "小图标",example = "http://wanquancaifu.oss-cn-shenzhen.aliyuncs.com/flag/cn.png")
    private String smallIcon;

    @ApiModelProperty(value = "大图标",example = "http://wanquancaifu.oss-cn-shenzhen.aliyuncs.com/flag/cn.png")
    private String bigIcon;

    @ApiModelProperty(value = "注释",example = "一个伟大的国家")
    private String notes;

}
