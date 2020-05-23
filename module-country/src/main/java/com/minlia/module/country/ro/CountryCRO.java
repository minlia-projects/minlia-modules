package com.minlia.module.country.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by garen on 2017/7/20.
 */
@Data
@ApiModel(value = "国家-创建")
public class CountryCRO {

    @ApiModelProperty(value = "名称",example = "中国")
    @NotBlank
    @Size(max = 20)
    private String name;

    @ApiModelProperty(value = "编码",example = "cn")
    @NotBlank
    @Pattern(regexp = "^[a-z_A-Z]{2,10}$")
    private String code;

    @ApiModelProperty(value = "语言",example = "zh_CN")
    @NotBlank
    private String language;

    @ApiModelProperty(value = "小图标",example = "http://wanquancaifu.oss-cn-shenzhen.aliyuncs.com/flag/cn.png")
    private String smallIcon;

    @ApiModelProperty(value = "大图标",example = "http://wanquancaifu.oss-cn-shenzhen.aliyuncs.com/flag/cn.png")
    private String bigIcon;

    @ApiModelProperty(value = "注释",example = "一个伟大的国家")
    private String notes;

}
