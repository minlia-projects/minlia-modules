package com.minlia.modules.rbac.mapper;


import com.minlia.modules.rbac.bean.qo.UserQO;
import com.minlia.modules.rbac.bean.domain.User;

import java.util.List;
import java.util.Set;

public interface UserMapper  {

    void create(User user);

    void update(User user);

    void delete(Long id);

    void grant(Long id, Set<Long> roles);

    long count(UserQO body);

    User queryOne(UserQO body);

    List<User> queryList(UserQO body);

    User queryByUsernameOrCellphoneOrEmail(String arg);

}
