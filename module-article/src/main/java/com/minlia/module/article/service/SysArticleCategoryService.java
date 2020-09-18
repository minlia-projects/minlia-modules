package com.minlia.module.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.article.entity.SysArticleCategoryEntity;
import com.minlia.module.article.bean.ArticleCategorySro;

import java.util.List;

/**
 * <p>
 * 文章类目 服务类
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
public interface SysArticleCategoryService extends IService<SysArticleCategoryEntity> {

    /**
     * 保存或更新
     *
     * @param cro
     * @return
     */
    SysArticleCategoryEntity saveOrUpdate(ArticleCategorySro cro);

    /**
     * 禁用/启用
     *
     * @param id
     * @return
     */
    boolean disable(Long id);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean delete(Long id);

    /**
     * 获取所有父级ID
     *
     * @param id
     * @return
     */
    List<Long> selectCategoryIdsById(Long id);

}