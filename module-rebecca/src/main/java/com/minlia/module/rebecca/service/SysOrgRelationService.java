package com.minlia.module.rebecca.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.rebecca.entity.SysOrgEntity;
import com.minlia.module.rebecca.entity.SysOrgRelationEntity;

/**
 * <p>
 * 组织-关系 服务类
 * </p>
 *
 * @author garen
 * @since 2021-03-02
 */
public interface SysOrgRelationService extends IService<SysOrgRelationEntity> {

    void create(SysOrgEntity sysOrg);

    void update(SysOrgEntity sysOrg);

    void delete(Long orgId);

}