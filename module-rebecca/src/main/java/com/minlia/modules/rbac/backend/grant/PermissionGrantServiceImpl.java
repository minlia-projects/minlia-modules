//package com.minlia.modules.rbac.backend.grant;
//
//import com.google.common.collect.Sets;
//import com.minlia.cloud.body.WithIdBody;
//import com.minlia.cloud.body.WithIdItemBody;
//import com.minlia.cloud.utils.ApiPreconditions;
//import com.minlia.modules.rbac.domain.Permission;
//import com.minlia.modules.rbac.domain.Role;
//import com.minlia.modules.rbac.repository.PermissionRepository;
//import com.minlia.modules.rbac.repository.RoleRepository;
//import com.minlia.modules.security.code.SecurityApiCode;
//import java.util.List;
//import java.util.Set;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
///**
// * Created by will on 6/19/17.
// * 授权服务实现
// */
//@Service
//@Slf4j
//public class PermissionGrantServiceImpl implements PermissionGrantService {
//
//
//  @Autowired
//  PermissionRepository permissionRepository;
//
//  @Autowired
//  RoleRepository roleRepository;
//
//
//  public Page<Permission> findAllPermissions(Pageable pageable) {
//    return permissionRepository.findAll(pageable);
//  }
//
//  @Cacheable(value = { "permission" }, key = "#p0+#p1",unless="#result==null")
//  public Page<Permission> findPermissionsByRoleId(Long roleId, Pageable pageable) {
//    return permissionRepository.findByRoles_Id(roleId, pageable);
//  }
//
//  @Override
//  public Page<Role> findRoleByUserId(Long userId, Pageable pageable) {
//    return roleRepository.findByUsers_Id(userId, pageable);
//  }
//
//  @Override
//  public Set<Permission> grantPermissionsByRoleId(Long roleId, WithIdItemBody body) {
//    log.debug("Grant permissions with {} for role {}", body.getItems(), roleId);
//
//    Role roleFound = roleRepository.findOne(roleId);
//    ApiPreconditions.checkNotNull(roleFound, SecurityApiCode.NOT_FOUND);
//
//    //清空当前已有的权限点
//    Set<Permission> newPermissions = Sets.newHashSet();
//    roleFound.setPermissions(null);
////        log.debug("Permissions found with size: {}", permissionsFound.size());
//    List<WithIdBody> items = body.getItems();
//
//    log.debug("With items {}", items);
//    if (null != items && items.size() > 0) {
//      Set<Permission> ret = Sets.newHashSet();
//      for (WithIdBody item : items) {
//        log.debug("Permission with id: {}", item.getId());
//        if (null != item.getId()) {
//          Permission permissionFound = permissionRepository.findOne(item.getId());
//          newPermissions.add(permissionFound);
////                    roleFound.addPermission(permissionFound);
//        }
//      }
//
//      log.debug("Before save RoleFound Permissions {}", roleFound.getPermissions());
//      roleFound.setPermissions(newPermissions);
//      Role roleUpdated = roleRepository.save(roleFound);
//
//    }
//    return Sets.newHashSet();
//  }
//
//
//}
