package com.minlia.module.rebecca.user.listener;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Sets;
import com.minlia.module.rebecca.permission.service.SysPermissionService;
import com.minlia.module.rebecca.role.entity.SysRoleEntity;
import com.minlia.module.rebecca.role.service.SysRoleService;
import com.minlia.module.rebecca.user.bean.SysUserCro;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.service.SysUserService;
import com.minlia.modules.security.constant.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
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
        initialAdmin();
        alreadySetup = true;
    }

    /**
     * 初始化管理员用户
     */
    private void initialAdmin() {
        if (sysUserService.count(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getUsername, "Admin")) == 0) {
            sysUserService.create(SysUserCro.builder()
                    .username("Admin")
                    .password(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, SecurityConstant.ROLE_ADMIN_CODE))
                    .defaultRole(SecurityConstant.ROLE_ADMIN_CODE)
                    .build());
        }
    }

}