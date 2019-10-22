package com.minlia.module.article.ro;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@ApiModel(value = "ArticleCRO")
@Data
public class ArticleCRO implements ApiRequestBody {

    @ApiModelProperty(value = "类目ID")
    @NotNull(message = "类目ID不能为空")
    private Long categoryId;

    @ApiModelProperty(value = "标签ID集合")
    private List<Long> labelIds;

    @NotBlank(message = "编码不能为空")
    @Size(max = 64)
    private String code;

    @NotBlank(message = "标题不能为空")
    @Size(max = 255)
    private String title;

    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 描述
     */
    @Size(max = 255)
    private String description;

    /**
     * 语言环境
     */
    @NotNull
    private LocaleEnum locale;

    /**
     * 草稿标识
     */
    private Boolean draftFlag;

    /**
     * 禁用标识
     */
    private Boolean disFlag;

    /**
     * 删除标识
     */
    private Boolean delFlag;

    /**
     * 阅读数
     */
    private Long readCount;

    /**
     * 封面
     */
    @Size(max = 255)
    private String cover;

    /**
     * 关键字
     */
    @Size(max = 255)
    private String keywords;

    /**
     * 备注
     */
    @Size(max = 255)
    private String remark;

    /**
     * 扩展字段1
     */
    @Size(max = 255)
    private String attribute1;

    /**
     * 扩展字段2
     */
    @Size(max = 255)
    private String attribute2;

    /**
     * 扩展字段3
     */
    @Size(max = 255)
    private String attribute3;

    /**
     * 扩展字段4
     */
    @Size(max = 255)
    private String attribute4;

    /**
     * 扩展字段5
     */
    @Size(max = 255)
    private String attribute5;

}
