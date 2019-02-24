package com.minlia.module.article.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.bean.domain.ArticleLabel;
import com.minlia.module.article.bean.qo.ArticleLabelQO;
import com.minlia.module.article.bean.to.ArticleLabelCTO;
import com.minlia.module.article.bean.to.ArticleLabelUTO;
import com.minlia.module.article.mapper.ArticleLabelMapper;
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
    public ArticleLabel create(ArticleLabelCTO cto) {
        ApiAssert.state(articleLabelMapper.count(ArticleLabelQO.builder().name(cto.getName()).build()) == 0, SystemCode.Message.DATA_ALREADY_EXISTS);
        ArticleLabel articleLabel = mapper.map(cto, ArticleLabel.class);
        articleLabelMapper.create(articleLabel);
        return articleLabel;
    }

    @Override
    public ArticleLabel update(ArticleLabelUTO uto) {
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
        return articleLabelMapper.one(ArticleLabelQO.builder().id(id).build());
    }

    @Override
    public long count(ArticleLabelQO qo) {
        return articleLabelMapper.count(qo);
    }

    @Override
    public ArticleLabel one(ArticleLabelQO qo) {
        return articleLabelMapper.one(qo);
    }

    @Override
    public List<ArticleLabel> list(ArticleLabelQO qo) {
        return articleLabelMapper.list(qo);
    }

    @Override
    public PageInfo<ArticleLabel> page(ArticleLabelQO qo, Pageable pageable) {
        return PageHelper.startPage(qo.getPageNumber(), qo.getPageSize()).doSelectPageInfo(()-> articleLabelMapper.list(qo));
    }

}
