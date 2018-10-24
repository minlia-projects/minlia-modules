package com.minlia.modules.rbac.service;


import com.minlia.modules.rbac.bean.to.UserCTO;
import com.minlia.modules.rbac.bean.to.UserUTO;
import com.minlia.modules.rbac.bean.domain.User;

import java.util.Set;

/**
 * 用户接口
 */
public interface UserService {

    User create(UserCTO entity);

    User update(UserUTO body);

    User update(User user);

    void delete(String guid);

    Boolean locked(String guid);

    Boolean disabled(String guid);

    void grant(String guid, Set<Long> roles);

    void grant(long id, Set<Long> roles);

}
