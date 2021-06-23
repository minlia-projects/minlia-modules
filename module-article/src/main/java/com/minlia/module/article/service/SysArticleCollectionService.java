package com.minlia.module.article.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.module.article.entity.SysArticleCollectionEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.data.entity.BaseQueryEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 文章收藏 服务类
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
public interface SysArticleCollectionService extends IService<SysArticleCollectionEntity> {

    /**
     * 收藏、取消收藏
     *
     * @param articleId
     * @return
     */
    boolean collection(Long articleId);

    /**
     * 是否收藏
     *
     * @param articleId
     * @return
     */
    boolean isCollected(Long articleId);

    /**
     * 我的收藏/分页查询
     *
     * @return
     */
    Page myPage(BaseQueryEntity qro);

}