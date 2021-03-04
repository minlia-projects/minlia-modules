package com.minlia.module.rebecca.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.rebecca.bean.SysOrganizationCro;
import com.minlia.module.rebecca.bean.SysOrganizationUro;
import com.minlia.module.rebecca.entity.SysOrgEntity;

/**
 * <p>
 * 组织 服务类
 * </p>
 *
 * @author garen
 * @since 2021-03-02
 */
public interface SysOrgService extends IService<SysOrgEntity> {

    SysOrgEntity create(SysOrganizationCro cro);

    boolean update(SysOrganizationUro uro);

    boolean delete(Long id);

}