package com.minlia.module.article.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 文章标签中间表-多对多
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_article_label_relation")
@ApiModel(value = "SysArticleLabelRelationEntity对象", description = "文章标签中间表-多对多")
public class SysArticleLabelRelationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章ID")
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty(value = "标签ID")
    @TableField("label_id")
    private Long labelId;


}
