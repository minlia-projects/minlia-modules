package com.minlia.module.article.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.entity.ArticleComment;
import com.minlia.module.article.ro.ArticleCommentQRO;
import com.minlia.module.article.ro.ArticleQRO;
import com.minlia.module.article.ro.ArticleCommentCRO;
import com.minlia.module.article.vo.ArticleCommentVO;
import com.minlia.module.article.vo.ArticleMyCommentVO;
import com.minlia.module.article.mapper.ArticleCommentMapper;
import com.minlia.module.article.service.ArticleCommentService;
import com.minlia.module.article.service.ArticleService;
import com.minlia.modules.rebecca.context.SecurityContextHolder;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 2018/09/27.
 */
@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleCommentMapper articleCommentMapper;

    @Override
    public ArticleComment create(ArticleCommentCRO cto) {
        long count = articleService.count(ArticleQRO.builder().id(cto.getArticleId()).build());
        ApiAssert.state(count == 1, SystemCode.Message.DATA_NOT_EXISTS);

//        ApiAssert.state(articleCommentMapper.count(ArticleCommentQRO.builder().articleId(cto.getArticleId()).createBy(SecurityContextHolder.getCurrentGuid()).build()) == 0, SystemCode.Message.DATA_ALREADY_EXISTS);

        ArticleComment articleComment = mapper.map(cto, ArticleComment.class);
        articleCommentMapper.create(articleComment);

        return articleComment;
    }

    @Override
    public void delete(Long id) {
        long count = articleCommentMapper.count(ArticleCommentQRO.builder().id(id).createBy(SecurityContextHolder.getCurrentGuid()).build());
        ApiAssert.state(count > 0, SystemCode.Message.DATA_NOT_EXISTS);
        articleCommentMapper.delete(id);
    }

    @Override
    public ArticleComment queryById(Long id) {
        return articleCommentMapper.queryById(id);
    }

    @Override
    public long count(ArticleCommentQRO qro) {
        return articleCommentMapper.count(qro);
    }

    @Override
    public List<ArticleComment> list(ArticleCommentQRO qro) {
        return articleCommentMapper.list(qro);
    }

    @Override
    public PageInfo<ArticleComment> page(ArticleCommentQRO qro, Pageable pageable) {
        return PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(()-> articleCommentMapper.list(qro));
    }

    @Override
    public List<ArticleCommentVO> queryDetailsList(ArticleCommentQRO qro) {
        return articleCommentMapper.queryDetailsList(qro);
    }

    @Override
    public PageInfo<ArticleCommentVO> queryDetailsPage(ArticleCommentQRO qro, Pageable pageable) {
        return PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(()-> articleCommentMapper.queryDetailsList(qro));
    }

    @Override
    public List<ArticleMyCommentVO> queryMyList(ArticleCommentQRO qro) {
        return articleCommentMapper.queryMyList(qro);
    }

    @Override
    public PageInfo<ArticleMyCommentVO> queryMyPage(ArticleCommentQRO qro, Pageable pageable) {
        return PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(()-> articleCommentMapper.queryMyList(qro));
    }

}
