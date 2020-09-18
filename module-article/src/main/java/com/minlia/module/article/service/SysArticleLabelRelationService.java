package com.minlia.module.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.article.entity.SysArticleLabelRelationEntity;

import java.util.Set;

/**
 * <p>
 * 文章标签中间表-多对多 服务类
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
public interface SysArticleLabelRelationService extends IService<SysArticleLabelRelationEntity> {

    /**
     * 保存文章-标签关系
     *
     * @param articleId
     * @param labelIds
     */
    void saveWithArticleIdAndLabelIds(Long articleId, Set<Long> labelIds);

}
