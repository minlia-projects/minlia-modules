package com.minlia.module.article.service;

import com.minlia.module.article.entity.SysArticleLabelEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 文章标签 服务类
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
public interface SysArticleLabelService extends IService<SysArticleLabelEntity> {

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean delete(Long id);

}
