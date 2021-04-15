package com.minlia.module.rebecca.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.rebecca.user.entity.SysUserRelationEntity;

/**
 * <p>
 * 用户-关系 服务类
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
public interface SysUserRelationService extends IService<SysUserRelationEntity> {

    void create(Long ancestor, Long descendant);

    void update(SysUserRelationEntity sysOrg);

    void delete(Long uid);

}