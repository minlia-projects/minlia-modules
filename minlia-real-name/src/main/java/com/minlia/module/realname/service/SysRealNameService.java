package com.minlia.module.realname.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.cloud.body.Response;
import com.minlia.module.realname.bean.SysRealNameCro;
import com.minlia.module.realname.entity.SysRealNameEntity;

/**
 * <p>
 * 实名认证 服务类
 * </p>
 *
 * @author garen
 * @since 2021-12-28
 */
public interface SysRealNameService extends IService<SysRealNameEntity> {

    Response auth(SysRealNameCro cro);

    boolean isAuthenticated(Long uid);

    SysRealNameEntity getAuthenticatedByUid(Long uid);

}