package com.minlia.modules.rbac.service;

import com.minlia.modules.rbac.dao.UserDao;
import com.minlia.modules.rbac.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/14/17.
 */
@Service
public class UserServiceImpl extends AbstractCrudService<UserDao, User, Long> implements UserService {

    @Autowired
    private UserDao userDao;

    public UserServiceImpl(com.minlia.modules.rbac.dao.UserDao repository) {
        super(repository);
    }


}
