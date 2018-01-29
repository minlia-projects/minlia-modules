package com.minlia.modules.rbac.backend.user.mapper;


import com.minlia.modules.rbac.backend.user.body.UserQueryRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper  {

    void create(User user);

    void update(User user);

    void delete(Long id);

    void locked(User user);

    void disabled(User user);

    User queryById(Long id);

    User queryByGuid(String guid);

    User queryByUsername(String username);

    User queryByCellphone(String cellphone);

    List<User> queryList(UserQueryRequestBody body);

    Page queryPage(UserQueryRequestBody body, Pageable pageable);

    User queryByUsernameOrEmailOrCellphone(String username, String email, String cellphone);

}
