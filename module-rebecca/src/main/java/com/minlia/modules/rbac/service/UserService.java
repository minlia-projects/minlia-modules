package com.minlia.modules.rbac.service;


import com.minlia.modules.rbac.domain.User;
import org.springframework.data.support.CrudService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 8/14/17.
 */
@Transactional(readOnly = true)
public interface UserService extends CrudService<User, Long> {

}
