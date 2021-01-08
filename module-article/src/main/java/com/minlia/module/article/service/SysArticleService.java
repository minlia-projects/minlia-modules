package com.minlia.module.article.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.module.article.entity.SysArticleEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.article.bean.ArticleQro;
import com.minlia.module.article.bean.ArticleSro;
import com.minlia.module.article.bean.vo.ArticleVo;

/**
 * <p>
 * 文章 服务类
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
public interface SysArticleService extends IService<SysArticleEntity> {

    /**
     * 保存或修改
     *
     * @param sro
     * @return
     */
    SysArticleEntity saveOrUpdate(ArticleSro sro);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean delete(Long id);

    /**
     * 添加阅读数
     *
     * @param id
     * @param increment
     */
    void plusReadCount(Long id, Integer increment);

    /**
     * 详情
     *
     * @param id
     * @return
     */
    ArticleVo details(Long id);

    /**
     * 详情
     *
     * @param code 编码
     * @return
     */
    ArticleVo details(String code);

    /**
     * 详情分页
     *
     * @param qro
     * @return
     */
    Page<ArticleVo> detailsPage(ArticleQro qro);

}
