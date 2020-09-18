package com.minlia.module.article.bean.vo;

import com.minlia.module.article.entity.SysArticleEntity;
import lombok.Data;

import java.util.List;

/**
 * 文章VO
 *
 * @author garen
 */
@Data
public class ArticleVo extends SysArticleEntity {

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 点赞数
     */
    private Integer praiseCount;

    /**
     * 收藏数
     */
    private Integer collectionCount;

    /**
     * 标签ID集
     */
    private List<Long> labelIds;

    /**
     * 标签名称集
     */
    private List<String> labelNames;

    private List<Long> categoryIds;

}