package com.minlia.module.i18n.ro;

import com.minlia.module.i18n.enumeration.LocaleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "i18n-create")
public class I18nCRO {

    @ApiModelProperty(value = "应用ID", example = "100000000")
    private String appid;

    @ApiModelProperty(value = "编码", example = "system.i18n.message.000001")
    @NotBlank
    private String code;

    @ApiModelProperty(value = "值", example = "国际化显示值")
    @NotBlank
    private String value;

    @ApiModelProperty(value = "语言", example = "zh_CN")
    @NotNull
    private LocaleEnum locale;

    //    /**
//     * 对应的语言
//     */
//    private String language;

}