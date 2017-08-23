package com.minlia.modules.rbac.service;


import com.minlia.cloud.service.WriteOnlyService;
import com.minlia.modules.rbac.dao.RoleDao;
import com.minlia.modules.rbac.domain.Role;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 8/14/17.
 */
@Transactional(readOnly = false)
public interface RoleWriteOnlyService extends WriteOnlyService<RoleDao, Role, Long> {

  public Role createRoleIfNotFound(Role role);

}
