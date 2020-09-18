package com.minlia.module.article.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.module.article.entity.SysArticlePraiseEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 文章赞、踩 服务类
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
public interface SysArticlePraiseService extends IService<SysArticlePraiseEntity> {

    boolean praise(Long articleId);

    boolean isPraised(Long articleId);

    Page page(int pageNumber, int pageSize);

}
