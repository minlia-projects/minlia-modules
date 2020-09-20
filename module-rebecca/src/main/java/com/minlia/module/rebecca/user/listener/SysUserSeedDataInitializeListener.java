package com.minlia.module.rebecca.user.listener;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.CaseFormat;
import com.minlia.module.rebecca.user.bean.SysUserCro;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.service.SysUserService;
import com.minlia.modules.security.constant.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author garen
 */
@Slf4j
@Component
@Order
public class SysUserSeedDataInitializeListener implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private final SysUserService sysUserService;

    public SysUserSeedDataInitializeListener(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        log.debug("Starting Initialize User Seed Data ");
        initialSuperAdmin();
        alreadySetup = true;
    }

    /**
     * 初始化超级管理员用户
     */
    private void initialSuperAdmin() {
        if (sysUserService.count(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getUsername, "Admin")) == 0) {
            sysUserService.create(SysUserCro.builder()
                    .username("admin")
//                    .password(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, SecurityConstant.ROLE_SUPER_ADMIN_CODE))
                    .password("admin")
                    .defaultRole(SecurityConstant.ROLE_SUPER_ADMIN_CODE)
                    .build());
        }
    }

}