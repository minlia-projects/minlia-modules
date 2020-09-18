package com.minlia.module.article.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.WithIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 文章收藏
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_article_collection")
@ApiModel(value = "SysArticleCollectionEntity对象", description = "文章收藏")
public class SysArticleCollectionEntity extends WithIdEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章ID")
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty(value = "操作人")
    @TableField("operator")
    private String operator;

    @ApiModelProperty(value = "收藏时间")
    @TableField("time")
    private LocalDateTime time;

}
