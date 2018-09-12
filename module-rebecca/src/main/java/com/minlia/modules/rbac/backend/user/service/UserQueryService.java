package com.minlia.modules.rbac.backend.user.service;


import com.github.pagehelper.PageInfo;
import com.minlia.modules.rbac.backend.user.body.UserQueryRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface UserQueryService {

    long count (UserQueryRequestBody requestBody);

    boolean exists(UserQueryRequestBody requestBody);

    User queryOne(UserQueryRequestBody requestBody);

    List<User> queryList(UserQueryRequestBody requestBody);

    PageInfo<User> queryPage(UserQueryRequestBody requestBody, Pageable pageable);

    User queryByGuidAndNotNull(String guid);

    User queryByUsernameOrCellphoneOrEmail(String arg);

}
