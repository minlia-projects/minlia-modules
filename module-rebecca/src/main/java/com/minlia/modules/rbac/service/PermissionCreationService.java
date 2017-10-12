package com.minlia.modules.rbac.service;

import com.minlia.modules.rbac.domain.Permission;
import com.minlia.modules.rbac.domain.Role;
import java.util.Map;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 6/18/17.
 */
@Transactional(readOnly = false)
public interface PermissionCreationService {

  @Transactional(readOnly = false)
  public Permission addPermission(String code, String label);

  @Transactional(readOnly = false)
  public void initialAdminPermissions(Map<String, String> initialAdminPermissions);


  @Transactional(readOnly = false)
  public void initialAdminPermissions(Set<Permission> permissions);
}
