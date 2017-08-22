//package com.minlia.module.bible.v1.listener;
//
//import com.google.common.collect.Maps;
//import com.minlia.module.bible.v1.service.BibleService;
//import com.minlia.module.security.constants.SecurityConstants;
//import com.minlia.module.security.v1.repository.RoleRepository;
//import com.minlia.module.security.v1.service.PermissionCreationService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Map;
//
//@Slf4j
//@Component
//public class BibleSeedDataInitializeListener implements ApplicationListener<ContextRefreshedEvent> {
//
//    private boolean alreadySetup = false;
//
//    @Autowired
//    private RoleRepository WSAFroleRepository;
//
//    @Autowired
//    PermissionCreationService permissionCreationService;
//
//    // API
//
//    @Override
//    @Transactional
//    public void onApplicationEvent(final ContextRefreshedEvent event) {
//        log.debug("Starting Initialize Bible Seed Data ");
//        if (alreadySetup) {
//            return;
//        }
//
//        //定义一个MAP, 根据MAP插入初始化数据
//        Map<String, String> initialAdminPermissions = Maps.newHashMap();
//
//        String entityName = "数据字典";
//        //菜单
//        initialAdminPermissions.put(BibleService.ENTITY_CREATE, SecurityConstants.CREATE_OPERATION_DESCRIPTION_CN + entityName);
//        initialAdminPermissions.put(BibleService.ENTITY_READ, SecurityConstants.READ_OPERATION_DESCRIPTION_CN + entityName);
//        initialAdminPermissions.put(BibleService.ENTITY_UPDATE, SecurityConstants.UPDATE_OPERATION_DESCRIPTION_CN + entityName);
//        initialAdminPermissions.put(BibleService.ENTITY_DELETE, SecurityConstants.DELETE_OPERATION_DESCRIPTION_CN + entityName);
//        initialAdminPermissions.put(BibleService.ENTITY_SEARCH, SecurityConstants.SEARCH_OPERATION_DESCRIPTION_CN + entityName);
//
//
//        permissionCreationService.initialAdminPermissions(initialAdminPermissions);
//
//        alreadySetup = true;
//    }
//
//
//}