package com.minlia.modules.rbac.service;

import com.google.common.collect.Sets;
import com.minlia.modules.rbac.dao.PermissionDao;
import com.minlia.modules.rbac.dao.RoleDao;
import com.minlia.modules.rbac.domain.Permission;
import com.minlia.modules.rbac.domain.Role;
import com.minlia.modules.rbac.repository.PermissionRepository;
import com.minlia.modules.rbac.repository.RoleRepository;
import com.minlia.modules.security.constant.SecurityConstant;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionCreationServiceImpl implements PermissionCreationService {

  @Autowired
  PermissionRepository permissionRepository;
  @Autowired
  PermissionDao permissionDao;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  RoleDao roleDao;

  public Permission addPermission(String code, String label) {
    Permission permission = permissionDao.findByCode(code);
    if (permission == null) {
      permission = new Permission();
      permission.setCode(code);
      permission.setLabel(label);
      permission = permissionDao.save(permission);
    }
    return permission;
  }

  @Override
  public void initialAdminPermissions(Map<String, String> initialAdminPermissions) {
    //添加权限点组管理员角色
    Role adminRole = roleDao.findOneByCode(SecurityConstant.ADMIN_ROLE_NAME);
//    ApiPreconditions.checkNotNull(adminRole, SecurityApiCode.ROLE_NOT_FOUND);
    if (null != adminRole) {
      Set<Permission> permissions = Sets.newHashSet();
      for (Map.Entry permission : initialAdminPermissions.entrySet()) {
        Permission created = addPermission((String) permission.getKey(),
            (String) permission.getValue());
        permissions.add(created);
      }
      adminRole.setPermissions(permissions);
      roleRepository.save(adminRole);
    }
  }

  /**
   * 初始化已经保存过的实体到ADMIN_ROLE
   */
  @Override
  public void initialAdminPermissions(Set<Permission> permissions) {
    Role adminRole = roleDao.findOneByCode(SecurityConstant.ADMIN_ROLE_NAME);
    if (null != adminRole) {
      adminRole.setPermissions(permissions);
      roleRepository.save(adminRole);
    }
  }
}
