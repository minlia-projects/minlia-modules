package com.minlia.modules.rbac.service;

import com.minlia.cloud.service.AbstractReadOnlyService;
import com.minlia.modules.rbac.dao.RoleDao;
import com.minlia.modules.rbac.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/14/17.
 */
@Service
public class RoleReadOnlyServiceImpl extends AbstractReadOnlyService<RoleDao,Role,Long> implements RoleReadOnlyService{

    @Autowired
    RoleDao userDao;
}
