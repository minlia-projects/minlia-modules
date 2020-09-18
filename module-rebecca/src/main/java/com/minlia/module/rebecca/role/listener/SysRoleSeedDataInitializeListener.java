package com.minlia.module.rebecca.role.listener;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.module.rebecca.permission.service.SysPermissionService;
import com.minlia.module.rebecca.role.entity.SysRoleEntity;
import com.minlia.module.rebecca.role.service.SysRoleService;
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
@Order(value = Ordered.LOWEST_PRECEDENCE - 1)
public class SysRoleSeedDataInitializeListener implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private final SysRoleService sysRoleService;
    private final SysPermissionService sysPermissionService;

    public SysRoleSeedDataInitializeListener(SysRoleService sysRoleService, SysPermissionService sysPermissionService) {
        this.sysRoleService = sysRoleService;
        this.sysPermissionService = sysPermissionService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        log.debug("Starting Initialize Role Seed Data ");
        initialRole();
        alreadySetup = true;
    }

    /**
     * 初始化默认角色
     */
    private void initialRole() {
        if (sysRoleService.count(Wrappers.<SysRoleEntity>lambdaQuery().eq(SysRoleEntity::getCode, SecurityConstant.ROLE_ADMIN_CODE)) == 0) {
            SysRoleEntity roleEntity = SysRoleEntity.builder().code(SecurityConstant.ROLE_ADMIN_CODE).name(SecurityConstant.ROLE_ADMIN_DESC).build();
            sysRoleService.save(roleEntity);
            //授权权限点
            sysRoleService.grantPermission(roleEntity.getId(), sysPermissionService.getAllIds());
        }
    }

}