package com.minlia.module.article.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.WithIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * <p>
 * 文章评论
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_article_comment")
@ApiModel(value = "SysArticleCommentEntity对象", description = "文章评论")
public class SysArticleCommentEntity extends WithIdEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章ID")
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty(value = "操作人")
    @TableField("operator")
    private Long operator;

    @ApiModelProperty(value = "时间")
    @TableField("time")
    private LocalDateTime time;

    @ApiModelProperty(value = " 内容")
    @TableField("content")
    private String content;

}