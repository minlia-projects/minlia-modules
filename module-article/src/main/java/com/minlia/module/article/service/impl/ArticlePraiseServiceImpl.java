package com.minlia.module.article.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.entity.ArticlePraise;
import com.minlia.module.article.ro.ArticlePraiseQRO;
import com.minlia.module.article.ro.ArticleQRO;
import com.minlia.module.article.ro.ArticlePraiseRO;
import com.minlia.module.article.mapper.ArticlePraiseMapper;
import com.minlia.module.article.service.ArticlePraiseService;
import com.minlia.module.article.service.ArticleService;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 2018/09/27.
 */
@Service
public class ArticlePraiseServiceImpl implements ArticlePraiseService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticlePraiseMapper articlePraiseMapper;

    @Override
    public Response operate(ArticlePraiseRO to) {
        long count = articleService.count(ArticleQRO.builder().id(to.getArticleId()).build());
        ApiAssert.state(count == 1, SystemCode.Message.DATA_NOT_EXISTS);

        ArticlePraise articlePraise = articlePraiseMapper.one(ArticlePraiseQRO.builder().articleId(to.getArticleId()).createBy(SecurityContextHolder.getCurrentGuid()).build());
        if (null == articlePraise) {
            articlePraise = ArticlePraise.builder().articleId(to.getArticleId()).choose(to.getChoose()).build();
            articlePraiseMapper.create(articlePraise);
            return Response.success(true);
        } else {
            //如果当前选则等于上次选择，直接删除记录
            if (to.getChoose().equals(articlePraise.getChoose())) {
                articlePraiseMapper.delete(articlePraise.getId());
                return Response.success(false);
            } else {
                articlePraise.setChoose(to.getChoose());
                articlePraiseMapper.update(articlePraise);
                return Response.success(!to.getChoose());
            }
        }
    }

    public Response praise(Long articleId, Boolean choose) {
        long count = articleService.count(ArticleQRO.builder().id(articleId).build());
        ApiAssert.state(count == 1, SystemCode.Message.DATA_NOT_EXISTS);

        ArticlePraise articlePraise = articlePraiseMapper.one(ArticlePraiseQRO.builder().articleId(articleId).createBy(SecurityContextHolder.getCurrentGuid()).build());
        if (null == articlePraise) {
            articlePraise = ArticlePraise.builder().articleId(articleId).choose(choose).build();
            articlePraiseMapper.create(articlePraise);
            return Response.success(true);
        } else {
            //如果当前选则等于上次选择，直接删除记录
            if (choose.equals(articlePraise.getChoose())) {
                articlePraiseMapper.create(articlePraise);
                return Response.success(false);
            } else {
                articlePraise.setChoose(choose);
                articlePraiseMapper.create(articlePraise);
                return Response.success(true);
            }
        }
    }

    @Override
    public Response trample(Long articleId) {
        long count = articleService.count(ArticleQRO.builder().id(articleId).build());
        ApiAssert.state(count == 1, SystemCode.Message.DATA_NOT_EXISTS);

        ArticlePraise articlePraise = articlePraiseMapper.one(ArticlePraiseQRO.builder().articleId(articleId).createBy(SecurityContextHolder.getCurrentGuid()).build());
        if (null == articlePraise) {
            articlePraise = ArticlePraise.builder().articleId(articleId).choose(false).build();
            articlePraiseMapper.create(articlePraise);
            return Response.success(true);
        } else {
            articlePraiseMapper.delete(articlePraise.getId());
            return Response.success(false);
        }
    }

    @Override
    public void delete(Long id) {
        ArticlePraise articlePraise = articlePraiseMapper.queryById(id);
        ApiAssert.notNull(articlePraise, SystemCode.Message.DATA_NOT_EXISTS);
        articlePraiseMapper.delete(id);
    }

    @Override
    public ArticlePraise queryById(Long id) {
        return articlePraiseMapper.queryById(id);
    }

    @Override
    public long count(ArticlePraiseQRO qro) {
        return articlePraiseMapper.count(qro);
    }

    @Override
    public ArticlePraise one(ArticlePraiseQRO qro) {
        return articlePraiseMapper.one(qro);
    }

    @Override
    public List<ArticlePraise> list(ArticlePraiseQRO qro) {
        return articlePraiseMapper.list(qro);
    }

    @Override
    public PageInfo<ArticlePraise> page(ArticlePraiseQRO qro, Pageable pageable) {
        return PageHelper.startPage(qro.getPageNumber(), qro.getPageSize()).doSelectPageInfo(()-> articlePraiseMapper.list(qro));
    }

}
