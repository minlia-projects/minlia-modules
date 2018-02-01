package com.minlia.modules.rbac.backend.user.listener;

import com.minlia.modules.rbac.backend.role.service.RoleService;
import com.minlia.modules.rbac.backend.user.service.UserQueryService;
import com.minlia.modules.rbac.backend.user.service.UserService;
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

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        log.debug("Starting Initialize User Seed Data ");

//        initialAdminInfo();
//
//        roleService.create(Role.builder().code(SecurityConstants.ROLE_USER_CODE).label(SecurityConstants.ROLE_USER_LABEL).build());
//        Set<Permission> permissions = initialPermissions();
//        roleService.grantPermission(SecurityConstants.ROLE_ADMIN_CODE,permissions);
//        roleService.grantPermission(SecurityConstants.ROLE_USER_CODE,permissions);

        alreadySetup = true;
    }

//    private Set<Permission> initialPermissions() {
//        Set<Permission> permissionCreated = Sets.newHashSet();
//
//        //租户同住人
//        permissionCreated.add(Permission.builder().code(AccountSecurityConstants.COTENANT_CREATE).label(SecurityConstants.OPERATION_CREATE_DESCRIPTION_CN).build());
//        permissionCreated.add(Permission.builder().code(AccountSecurityConstants.COTENANT_UPDATE).label(SecurityConstants.OPERATION_UPDATE_DESCRIPTION_CN).build());
//        permissionCreated.add(Permission.builder().code(AccountSecurityConstants.COTENANT_DELETE).label(SecurityConstants.OPERATION_DELETE_DESCRIPTION_CN).build());
//        permissionCreated.add(Permission.builder().code(AccountSecurityConstants.COTENANT_SEARCH).label(SecurityConstants.OPERATION_SEARCH_DESCRIPTION_CN).build());
//        permissionCreated.add(Permission.builder().code(AccountSecurityConstants.COTENANT_READ).label(SecurityConstants.OPERATION_READ_DESCRIPTION_CN).build());
//
//        //用户账户
//        permissionCreated.add(Permission.builder().code(AccountSecurityConstants.USER_SEARCH).label(SecurityConstants.OPERATION_READ_DESCRIPTION_CN).build());
//        permissionCreated.add(Permission.builder().code(AccountSecurityConstants.USER_READ).label(SecurityConstants.OPERATION_SEARCH_DESCRIPTION_CN).build());
//
//        return permissionService.create(permissionCreated);
//    }
//
//    /**
//     * 初始化管理员用户信息
//     */
//    private void initialAdminInfo() {
//        User user = userQueryService.findByUsername("admin");
//        if (null != user) {
//            UserInfo userInfo = userInfoService.findByUserId(user.getId());
//            if (null == userInfo) {
//                userInfoService.init(user);
//                propertyholderAccountService.create(user);
//            }
//        }
//    }

}