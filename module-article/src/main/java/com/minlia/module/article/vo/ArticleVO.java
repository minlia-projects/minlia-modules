package com.minlia.module.article.vo;

import com.minlia.module.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 文章VO
 */
@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class ArticleVO extends Article {

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

//    /**
//     * 昵称
//     */
//    private String nickname;
//
//    /**
//     * 头像
//     */
//    private String avatar;

}
