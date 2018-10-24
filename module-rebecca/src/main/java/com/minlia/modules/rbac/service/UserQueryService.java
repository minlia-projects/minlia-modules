package com.minlia.modules.rbac.service;


import com.github.pagehelper.PageInfo;
import com.minlia.modules.rbac.bean.qo.UserQO;
import com.minlia.modules.rbac.bean.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface UserQueryService {

    long count (UserQO requestBody);

    boolean exists(UserQO requestBody);

    User queryOne(UserQO requestBody);

    List<User> queryList(UserQO requestBody);

    PageInfo<User> queryPage(UserQO requestBody, Pageable pageable);

    User queryByGuidAndNotNull(String guid);

    User queryByUsernameOrCellphoneOrEmail(String arg);

}
