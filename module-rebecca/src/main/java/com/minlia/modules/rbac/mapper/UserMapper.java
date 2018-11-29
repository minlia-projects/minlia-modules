package com.minlia.modules.rbac.mapper;


import com.minlia.modules.rbac.bean.domain.User;
import com.minlia.modules.rbac.bean.qo.UserQO;

import java.util.List;
import java.util.Set;

public interface UserMapper  {

    void create(User user);

    void update(User user);

    void delete(Long id);

    void grant(Long id, Set<Long> roles);

    long count(UserQO qo);

    User queryOne(UserQO qo);

    List<User> queryList(UserQO qo);

    User queryByUsernameOrCellphoneOrEmail(String arg);

}
