package com.minlia.modules.rbac.backend.user.service;


import com.minlia.modules.rbac.backend.user.body.UserQueryRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface UserQueryService {

    User queryById(Long id);

    User queryByGuid(String guid);

    User queryByGuidAndNotNull(String guid);

    User queryByUsername(String username);

    User queryByCellphone(String cellphone);

    /**
     * 用户名、手机号码、邮箱
     * @param username
     * @return
     */
    boolean exists(String username);

    List<User> queryList(UserQueryRequestBody body);

    Page<User> queryPage(UserQueryRequestBody body, Pageable pageable);

    User queryByUsernameOrCellphoneOrEmail(String username, String email, String cellphone);

}
