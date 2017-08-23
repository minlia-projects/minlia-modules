package com.minlia.modules.rbac.service;

import com.minlia.modules.rbac.domain.Permission;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 6/18/17.
 */
@Transactional(readOnly = false)
public interface PermissionCreationService {

  public Permission addPermission(String code, String label);

  public void initialAdminPermissions(Map<String, String> initialAdminPermissions);
}
