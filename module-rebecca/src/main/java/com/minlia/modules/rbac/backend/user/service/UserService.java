package com.minlia.modules.rbac.backend.user.service;


import com.minlia.modules.rbac.backend.user.body.UserCreateRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserGarenRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserUpdateRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;

/**
 * 用户接口
 */
public interface UserService {

    User create(UserCreateRequestBody entity);

    User update(UserUpdateRequestBody body);

    User update(User user);

    void delete(String guid);

    Boolean locked(String guid);

    void grant(UserGarenRequestBody requestBody);

    Boolean disabled(String guid);

}
