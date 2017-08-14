package com.minlia.modules.rbac.service;

import com.minlia.cloud.data.batis.service.AbstractQueryService;
import com.minlia.modules.rbac.dao.UserDao;
import com.minlia.modules.rbac.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/14/17.
 */
@Service
public class UserQueryServiceImpl extends AbstractQueryService implements UserQueryService {

    @Autowired
    UserDao userDao;
    /**
     * 调用Dao进行查询
     *
     * @param login
     * @return
     */
    @Override
    public User findOneByUsernameOrEmailOrCellphone(String login) {
        return userDao.findOneByUsernameOrEmailOrCellphone(login,login,login);
    }
}
