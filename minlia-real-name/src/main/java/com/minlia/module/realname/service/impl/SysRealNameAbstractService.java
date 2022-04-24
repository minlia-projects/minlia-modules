package com.minlia.module.realname.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.body.Response;
import com.minlia.module.realname.bean.SysRealNameCro;
import com.minlia.module.realname.config.SysRealNameAuthConfig;
import com.minlia.module.realname.entity.SysRealNameEntity;
import com.minlia.module.realname.enums.SysAuthenticationStatusEnum;
import com.minlia.module.realname.mapper.SysRealNameMapper;
import com.minlia.module.realname.service.SysRealNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 实名认证 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-12-28
 */
@Service
@RequiredArgsConstructor
public abstract class SysRealNameAbstractService extends ServiceImpl<SysRealNameMapper, SysRealNameEntity> implements SysRealNameService {

    private final SysRealNameAuthConfig sysRealNameAuthConfig;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public abstract Response auth(SysRealNameCro cro);

    @Override
    public boolean isAuthenticated(Long uid) {
        return this.count(Wrappers.<SysRealNameEntity>lambdaQuery().eq(SysRealNameEntity::getUid, uid).eq(SysRealNameEntity::getStatus, SysAuthenticationStatusEnum.PASSED)) > 0;
    }

    @Override
    public SysRealNameEntity getAuthenticatedByUid(Long uid) {
        return this.getOne(Wrappers.<SysRealNameEntity>lambdaQuery().eq(SysRealNameEntity::getUid, uid).eq(SysRealNameEntity::getStatus, SysAuthenticationStatusEnum.PASSED));
    }

    public SysRealNameAuthConfig getConfig() {
        return sysRealNameAuthConfig;
    }

}