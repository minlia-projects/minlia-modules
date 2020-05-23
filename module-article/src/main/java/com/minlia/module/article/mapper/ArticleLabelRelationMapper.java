package com.minlia.module.article.mapper;

import com.minlia.module.article.entity.ArticleLabelRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleLabelRelationMapper {

    int insertSelective(ArticleLabelRelation articleLabelRelation);

    int insertBatch(@Param("articleId") Long articleId, @Param("labelIds") List<Long> labelIds);

    int deleteByArticleId(@Param("articleId") Long articleId);

    int deleteByLabelId(@Param("labelId") Long labelId);

}