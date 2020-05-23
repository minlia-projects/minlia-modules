package com.minlia.module.article.mapper;

import com.minlia.module.article.entity.ArticleCategory;
import com.minlia.module.article.ro.ArticleCategoryQRO;
import com.minlia.module.article.vo.ArticleSimpleVO;

import java.util.List;

public interface ArticleCategoryMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(ArticleCategory record);

    ArticleCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleCategory record);



    List<ArticleSimpleVO> queryArticleByCategoryId(Long id);

    long countByAll(ArticleCategoryQRO qro);

    List<ArticleCategory> selectByAll(ArticleCategoryQRO qro);

    Long selectParentIdById(Long id);

}