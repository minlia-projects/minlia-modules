package com.minlia.modules.rbac.service;

import com.minlia.cloud.service.AbstractReadOnlyService;
import com.minlia.modules.rbac.dao.UserDao;
import com.minlia.modules.rbac.domain.User;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/14/17.
 */
@Service
@Slf4j
@CacheConfig(cacheNames = { "user" })
public class UserReadOnlyServiceImpl extends AbstractReadOnlyService<UserDao,User,Long> implements
    UserReadOnlyService {

    @Autowired
    UserDao userDao;

    @Autowired
    DataSource dataSource;
    /**
     * 调用Dao进行查询
     *
     * @param login
     * @return
     */
    @Override
    @Cacheable(value = { "user" }, key = "#p0",unless="#result==null")
    public User findOneByUsernameOrEmailOrCellphone(String login) {
//        try {
//            log.info("UserReadOnlyServiceImpl.findOneByUsernameOrEmailOrCellphone {}",dataSource.getConnection().getCatalog());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return userDao.findOneByUsernameOrEmailOrCellphone(login,login,login);
    }
}
