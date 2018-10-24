//package com.minlia.modules.rbac.backend.listener;
//
//import com.google.common.collect.Sets;
//import com.minlia.cloud.utils.Environments;
//import com.minlia.module.language.v1.service.LanguageInitializeService;
//import com.minlia.modules.rbac.context.PermissionContextHolder;
//import com.minlia.modules.rbac.domain.Permission;
//import com.minlia.modules.rbac.config.RebeccaProperties;
//import com.minlia.modules.rbac.service.PermissionCreationService;
//import java.util.Set;
//import javax.annotation.Priority;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.core.Ordered;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StopWatch;
//
///**
// * 使用系统级别的 Language 进行国际化配置
// */
//@Slf4j
//@Component
//@Priority(value = Ordered.HIGHEST_PRECEDENCE+1)
//public class SecuredAnnotationInitializingListener implements
//    ApplicationListener<ApplicationReadyEvent> {
//
//  @Autowired
//  PermissionCreationService permissionCreationService;
//
//  @Autowired
//  LanguageInitializeService languageInitializeService;
//
//  @Autowired
//  RebeccaProperties rebeccaProperties;
//
//  /**
//   * 获取到所有注解的类,初始化到数据库
//   */
//  @Override
//  public void onApplicationEvent(ApplicationReadyEvent event) {
//
//    if((null== rebeccaProperties.getEnableSecuredAnnotationScan()) || (!rebeccaProperties.getEnableSecuredAnnotationScan())){
//      return;
//    }
//
//    log.debug("Starting initialize annotation permissions");
//    StopWatch watch = new StopWatch();
//    watch.start();
//    if (Environments.isProduction()) {
//      return;
//    }
//
//    Set<String> permissions = PermissionContextHolder.get();
////    Map<String, String> adminPermissions = Maps.newConcurrentMap();
//    Set<Permission> permissionEntities= Sets.newHashSet();
//    for (String permission : permissions) {
//      permissionEntities.add(permissionCreationService.addPermission(permission, permission));
//      languageInitializeService.initialLanguage(permission);
////      adminPermissions.put(permission, permission);
//    }
////    permissionCreationService.initialAdminPermissions(adminPermissions);
//    permissionCreationService.initialAdminPermissions(permissionEntities);
//
//    watch.stop();
//    log.debug("Completed initialize annotation permissions in {} ms", watch.getTotalTimeMillis());
//  }
//}