package com.minlia.module.rebecca.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.rebecca.entity.SysDataPermissionEntity;

/**
 * <p>
 * 数据权限 服务类
 * </p>
 *
 * @author garen
 * @since 2021-03-03
 */
public interface SysDataPermissionService extends IService<SysDataPermissionEntity> {

    boolean createAllSelect(String className);

    boolean create(SysDataPermissionEntity entity);

    boolean update(SysDataPermissionEntity entity);

    boolean delete(Long id);

}
