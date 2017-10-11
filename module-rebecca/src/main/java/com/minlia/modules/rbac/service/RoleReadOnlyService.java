package com.minlia.modules.rbac.service;

import com.minlia.cloud.service.ReadOnlyService;
import com.minlia.modules.rbac.dao.RoleDao;
import com.minlia.modules.rbac.domain.Role;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 8/14/17.
 */
@Transactional(readOnly = true)
public interface RoleReadOnlyService extends ReadOnlyService<RoleDao,Role,Long> {

  public Role findOneByCode(String code);

}
