package com.minlia.modules.rbac.service;


import com.minlia.cloud.service.WriteOnlyService;
import com.minlia.modules.rbac.dao.UserDao;
import com.minlia.modules.rbac.domain.User;
import org.springframework.data.support.CrudService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 8/14/17.
 */
@Transactional(readOnly = true)
public interface UserService extends WriteOnlyService<UserDao,User, Long> {

}
