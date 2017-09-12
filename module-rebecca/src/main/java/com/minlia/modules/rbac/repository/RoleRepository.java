package com.minlia.modules.rbac.repository;

import com.minlia.cloud.repository.AbstractRepository;
import com.minlia.modules.rbac.domain.Permission;
import com.minlia.modules.rbac.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by will on 8/23/17.
 */
public interface RoleRepository extends AbstractRepository<Role,Long> {
  Role findOneByCode(String code);

  Page<Role> findByUsers_Id(Long userId, Pageable pageable);

}
