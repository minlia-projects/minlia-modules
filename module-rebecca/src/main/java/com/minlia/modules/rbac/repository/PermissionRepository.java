package com.minlia.modules.rbac.repository;

import com.minlia.cloud.repository.AbstractRepository;
import com.minlia.modules.rbac.domain.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by will on 8/23/17.
 */
public interface PermissionRepository extends AbstractRepository<Permission,Long> {

  Page<Permission> findByRoles_Id(Long roleId, Pageable pageable);
}
