package com.minlia.modules.rbac.service;

import com.minlia.cloud.service.ReadOnlyService;
import com.minlia.modules.rbac.dao.PermissionDao;
import com.minlia.modules.rbac.domain.Permission;
import com.minlia.modules.rbac.domain.Role;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 8/14/17.
 */
@Transactional(readOnly = false)
public interface PermissionReadOnlyService extends ReadOnlyService<PermissionDao,Permission,Long> {


    List<GrantedAuthority> findPermissionsByRoles(Collection<Role> roles);
}
