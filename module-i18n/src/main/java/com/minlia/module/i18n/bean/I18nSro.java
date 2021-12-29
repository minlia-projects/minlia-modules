package com.minlia.module.i18n.bean;

import com.minlia.module.i18n.enums.LocaleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author garen
 */
@Data
@ApiModel(value = "i18n-sro")
public class I18nSro {

    @ApiModelProperty(value = "主键")
    @Min(0)
    private Long id;

    @ApiModelProperty(value = "应用ID", example = "100000000")
    @Size(max = 15)
    private String appid;

    @ApiModelProperty(value = "编码", example = "system.i18n.message.000001")
    @NotBlank
    @Size(max = 100)
    private String code;

    @ApiModelProperty(value = "值", example = "国际化显示值")
    @NotBlank
    @Size(max = 200)
    private String value;

    @ApiModelProperty(value = "语言", example = "zh_CN")
    @NotNull
    private LocaleEnum locale;

    @ApiModelProperty(value = "语言")
    @Size(max = 15)
    private String language;

}