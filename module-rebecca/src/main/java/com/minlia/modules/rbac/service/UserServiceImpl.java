package com.minlia.modules.rbac.service;

import com.minlia.cloud.service.AbstractWriteOnlyService;
import com.minlia.modules.rbac.dao.UserDao;
import com.minlia.modules.rbac.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/14/17.
 */
@Service
public class UserServiceImpl extends AbstractWriteOnlyService<UserDao, User, Long> implements UserService {

    @Autowired
    private UserDao userDao;


}
