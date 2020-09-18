package com.minlia.module.article.bean;

import com.minlia.module.data.bean.QueryRequest;
import com.minlia.module.data.entity.BaseQueryEntity;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author garen
 */
@Data
public class ArticleLabelQro extends BaseQueryEntity {

    @ApiModelProperty(value = "编码", example = "HOME")
    @Size(max = 64)
    private String code;

    @ApiModelProperty(value = "名称", example = "首页")
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "语言环境", example = "zh_CN")
    private LocaleEnum locale;

    @ApiModelProperty(value = "备注", example = "XXXXXX")
    @Size(max = 255)
    private String remark;

    @ApiModelProperty(value = "禁用标识")
    private Boolean disFlag;

}