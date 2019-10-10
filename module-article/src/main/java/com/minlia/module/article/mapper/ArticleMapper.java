package com.minlia.module.article.mapper;
import org.apache.ibatis.annotations.Param;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import java.time.LocalDateTime;

import com.minlia.module.article.entity.Article;
import com.minlia.module.article.ro.ArticleQRO;
import com.minlia.module.article.ro.ArticleSetLabelRO;
import com.minlia.module.article.vo.ArticleVO;

import java.util.List;

public interface ArticleMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(Article article);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article article);



    long plusReadCount(Long id, Integer increment);

    long countByAll(ArticleQRO qro);

    List<Article> selectByAll(ArticleQRO qro);

    ArticleVO oneVO(ArticleQRO qro);

    List<ArticleVO> listVO(ArticleQRO qro);

}