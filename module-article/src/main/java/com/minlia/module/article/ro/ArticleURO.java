package com.minlia.module.article.ro;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@ApiModel(value = "ArticleURO")
@Data
public class ArticleURO implements ApiRequestBody {

    @ApiModelProperty(value = "ID", example = "1")
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 类目ID
     */
    private Long categoryId;
    private List<Long> categoryIds;

    @ApiModelProperty(value = "标签ID集合")
    private List<Long> labelIds;

    @Size(max = 64)
    private String code;

    /**
     * 标题
     */
    @Size(max = 255)
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 描述
     */
    @Size(max = 255)
    private String description;

    /**
     * 语言环境
     */
//    private LocaleEnum locale;

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
