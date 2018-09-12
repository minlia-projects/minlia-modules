package com.minlia.modules.rbac.backend.user.mapper;


import com.minlia.modules.rbac.backend.user.body.UserQueryRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;

import java.util.List;
import java.util.Set;

public interface UserMapper  {

    void create(User user);

    void update(User user);

    void delete(Long id);

    void grant(Long id, Set<Long> roles);

    long count(UserQueryRequestBody body);

    User queryOne(UserQueryRequestBody body);

    List<User> queryList(UserQueryRequestBody body);

    User queryByUsernameOrCellphoneOrEmail(String arg);

}
