package com.minlia.module.article.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.entity.ArticleLabel;
import com.minlia.module.article.ro.ArticleLabelQRO;
import com.minlia.module.article.ro.ArticleLabelCRO;
import com.minlia.module.article.ro.ArticleLabelURO;
import com.minlia.module.article.mapper.ArticleLabelMapper;
import com.minlia.module.article.service.ArticleLabelService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 2018/09/27.
 */
@Service
public class ArticleLabelServiceImpl implements ArticleLabelService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ArticleLabelMapper articleLabelMapper;

    @Override
    public ArticleLabel create(ArticleLabelCRO cto) {
        ApiAssert.state(articleLabelMapper.count(ArticleLabelQRO.builder().name(cto.getName()).build()) == 0, SystemCode.Message.DATA_ALREADY_EXISTS);
        ArticleLabel articleLabel = mapper.map(cto, ArticleLabel.class);
        articleLabelMapper.create(articleLabel);
        return articleLabel;
    }

    @Override
    public ArticleLabel update(ArticleLabelURO uto) {
        ArticleLabel articleLabel = this.queryById(uto.getId());
        ApiAssert.notNull(articleLabel, SystemCode.Message.DATA_NOT_EXISTS);
        mapper.map(uto, articleLabel);
        articleLabelMapper.update(articleLabel);
        return articleLabel;
    }

    @Override
    public void delete(Long id) {
        ArticleLabel articleLabel = this.queryById(id);
        ApiAssert.notNull(articleLabel, SystemCode.Message.DATA_NOT_EXISTS);
        articleLabelMapper.delete(articleLabel.getId());
    }

    @Override
    public ArticleLabel queryById(Long id) {
        return articleLabelMapper.one(ArticleLabelQRO.builder().id(id).build());
    }

    @Override
    public long count(ArticleLabelQRO qo) {
        return articleLabelMapper.count(qo);
    }

    @Override
    public ArticleLabel one(ArticleLabelQRO qo) {
        return articleLabelMapper.one(qo);
    }

    @Override
    public List<ArticleLabel> list(ArticleLabelQRO qo) {
        return articleLabelMapper.list(qo);
    }

    @Override
    public PageInfo<ArticleLabel> page(ArticleLabelQRO qo, Pageable pageable) {
        return PageHelper.startPage(qo.getPageNumber(), qo.getPageSize(), qro.getOrderBy(), qro.getOrderBy()).doSelectPageInfo(()-> articleLabelMapper.list(qo));
    }

}
