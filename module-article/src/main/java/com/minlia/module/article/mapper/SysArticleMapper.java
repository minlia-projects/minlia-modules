package com.minlia.module.article.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.module.article.entity.SysArticleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minlia.module.article.bean.vo.ArticleVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 文章 Mapper 接口
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
public interface SysArticleMapper extends BaseMapper<SysArticleEntity> {

    ArticleVo selectDetailsById(@Param("id") Long id);

    ArticleVo selectDetailsByCode(@Param("code") String code, @Param("locale") String locale);

    Page<ArticleVo> pageVo(@Param("page") Page page, @Param("ew") LambdaQueryWrapper<Object> ew);

    int plusReadCount(@Param("id") Long id, @Param("increment") Integer increment);

}