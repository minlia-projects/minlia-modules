package com.minlia.modules.rbac.service;

import com.minlia.cloud.service.ReadOnlyService;
import com.minlia.modules.rbac.dao.UserDao;
import com.minlia.modules.rbac.domain.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 8/14/17.
 */
@Transactional(readOnly = true)
public interface UserQueryService extends ReadOnlyService<UserDao,User,Long> {
    /**
     * 自动根据登录账户从数据库里获取用户
     * @return
     */
    public User findOneByUsernameOrEmailOrCellphone(String login);


}
