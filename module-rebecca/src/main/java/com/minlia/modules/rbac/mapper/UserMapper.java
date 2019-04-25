package com.minlia.modules.rbac.mapper;


import com.minlia.modules.rbac.bean.domain.User;
import com.minlia.modules.rbac.bean.qo.UserQO;

import java.util.List;
import java.util.Set;

public interface UserMapper  {

    int create(User user);

    int update(User user);

    int delete(Long id);

    void grant(Long id, Set<Long> roles);

    long count(UserQO qro);

    User queryOne(UserQO qro);

    List<User> queryList(UserQO qro);

    User queryByUsernameOrCellphoneOrEmail(String arg);

}
