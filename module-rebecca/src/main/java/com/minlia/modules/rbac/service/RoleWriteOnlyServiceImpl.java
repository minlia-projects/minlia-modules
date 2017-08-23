package com.minlia.modules.rbac.service;

import com.minlia.cloud.service.AbstractWriteOnlyService;
import com.minlia.modules.rbac.dao.RoleDao;
import com.minlia.modules.rbac.domain.Role;
import com.minlia.modules.rbac.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/14/17.
 */
@Service
public class RoleWriteOnlyServiceImpl extends
    AbstractWriteOnlyService<RoleDao, Role, Long> implements
    RoleWriteOnlyService {


  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Role createRoleIfNotFound(Role roleToBeCreated) {
    Role role = roleRepository.findOneByCode(roleToBeCreated.getCode());
    if (role == null) {
      role = new Role();
      role.setCode(roleToBeCreated.getCode());
      role.setLabel(roleToBeCreated.getLabel());
      role.setPermissions(roleToBeCreated.getPermissions());
      roleRepository.save(role);
    }
    return role;
  }

}
