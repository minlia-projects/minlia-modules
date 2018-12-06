package com.minlia.module.article.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.bean.domain.ArticleComment;
import com.minlia.module.article.bean.qo.ArticleCommentQO;
import com.minlia.module.article.bean.qo.ArticleQO;
import com.minlia.module.article.bean.to.ArticleCommentCTO;
import com.minlia.module.article.bean.vo.ArticleCommentVO;
import com.minlia.module.article.bean.vo.ArticleMyCommentVO;
import com.minlia.module.article.mapper.ArticleCommentMapper;
import com.minlia.modules.rbac.context.SecurityContextHolder;
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
    public ArticleComment create(ArticleCommentCTO cto) {
        long count = articleService.count(ArticleQO.builder().id(cto.getArticleId()).build());
        ApiAssert.state(count == 1, SystemCode.Message.DATA_NOT_EXISTS);

        ApiAssert.state(articleCommentMapper.count(ArticleCommentQO.builder().articleId(cto.getArticleId()).createBy(SecurityContextHolder.getCurrentGuid()).build()) == 0, SystemCode.Message.DATA_ALREADY_EXISTS);

        ArticleComment articleComment = mapper.map(cto, ArticleComment.class);
        articleCommentMapper.create(articleComment);

        return articleComment;
    }

    @Override
    public void delete(Long id) {
        long count = articleCommentMapper.count(ArticleCommentQO.builder().id(id).createBy(SecurityContextHolder.getCurrentGuid()).build());
        ApiAssert.state(count > 0, SystemCode.Message.DATA_NOT_EXISTS);
        articleCommentMapper.delete(id);
    }

    @Override
    public ArticleComment queryById(Long id) {
        return articleCommentMapper.queryById(id);
    }

    @Override
    public long count(ArticleCommentQO qo) {
        return articleCommentMapper.count(qo);
    }

    @Override
    public List<ArticleComment> list(ArticleCommentQO qo) {
        return articleCommentMapper.list(qo);
    }

    @Override
    public PageInfo<ArticleComment> page(ArticleCommentQO qo, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> articleCommentMapper.list(qo));
    }

    @Override
    public List<ArticleCommentVO> queryDetailsList(ArticleCommentQO qo) {
        return articleCommentMapper.queryDetailsList(qo);
    }

    @Override
    public PageInfo<ArticleCommentVO> queryDetailsPage(ArticleCommentQO qo, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> articleCommentMapper.queryDetailsList(qo));
    }

    @Override
    public List<ArticleMyCommentVO> queryMyList(ArticleCommentQO qo) {
        return articleCommentMapper.queryMyList(qo);
    }

    @Override
    public PageInfo<ArticleMyCommentVO> queryMyPage(ArticleCommentQO qo, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> articleCommentMapper.queryMyList(qo));
    }

}
