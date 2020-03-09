package com.minlia.module.article.ro;

import com.minlia.module.data.bean.QueryRequest;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCategoryQRO extends QueryRequest {

    private Long id;

    private String createBy;

    private String lastModifiedBy;

    private LocalDateTime createDate;

    private LocalDateTime lastModifiedDate;

    private Long parentId;

    private Boolean isLeaf;

    @ApiModelProperty(value = "名称", example = "首页")
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "编码", example = "XXXXXX")
    @Size(max = 50)
    private String code;

    /**
     * 语言环境
     */
    private LocaleEnum locale;

    @ApiModelProperty(value = "备注", example = "XXXXXX")
    @Size(max = 255)
    private String remark;

    @ApiModelProperty(value = "禁用标识")
    private Boolean disFlag;

    private Boolean delFlag;

}