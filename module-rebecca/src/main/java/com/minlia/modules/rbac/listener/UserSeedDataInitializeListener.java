package com.minlia.modules.rbac.listener;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Sets;
import com.minlia.modules.rbac.backend.permission.service.PermissionService;
import com.minlia.modules.rbac.backend.role.body.RoleCreateRequestBody;
import com.minlia.modules.rbac.backend.role.entity.Role;
import com.minlia.modules.rbac.backend.role.service.RoleService;
import com.minlia.modules.rbac.backend.user.body.UserCreateRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserQueryRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.service.UserQueryService;
import com.minlia.modules.rbac.backend.user.service.UserService;
import com.minlia.modules.security.constant.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE + 10)
public class UserSeedDataInitializeListener implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserQueryService userQueryService;

    @Autowired
    private PermissionService permissionService;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        log.debug("Starting Initialize User Seed Data ");
        initialRole();
        initialAdmin();
        alreadySetup = true;
    }

    /**
     * 初始化默认角色
     */
    private void initialRole() {
        if (!roleService.exists(SecurityConstant.ROLE_ADMIN_CODE)) {
            Role role = roleService.create(RoleCreateRequestBody.builder().code(SecurityConstant.ROLE_ADMIN_CODE).label(SecurityConstant.ROLE_ADMIN_DESC).build());
            permissionService.grantAll(role.getId());
        }
        if (!roleService.exists(SecurityConstant.ROLE_USER_CODE)) {
            roleService.create(RoleCreateRequestBody.builder().code(SecurityConstant.ROLE_USER_CODE).label(SecurityConstant.ROLE_USER_DESC).build());
        }
        if (!roleService.exists(SecurityConstant.ROLE_GUEST_CODE)) {
            roleService.create(RoleCreateRequestBody.builder().code(SecurityConstant.ROLE_GUEST_CODE).label(SecurityConstant.ROLE_GUEST_DESC).build());
        }
    }

    /**
     * 初始化管理员用户
     */
    private User initialAdmin() {
        User user = userQueryService.queryOne(UserQueryRequestBody.builder().username(SecurityConstant.ROLE_ADMIN_CODE).build());
        if (null == user) {
            Role role = roleService.queryByCode(SecurityConstant.ROLE_ADMIN_CODE);
            user = userService.create(UserCreateRequestBody.builder()
                    .username(SecurityConstant.ROLE_ADMIN_CODE)
                    .password(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,SecurityConstant.ROLE_ADMIN_CODE))
                    .roles(Sets.newHashSet(role.getId()))
                    .build());
        }
        return user;
    }

}