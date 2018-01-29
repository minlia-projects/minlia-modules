package com.minlia.modules.rbac.backend.user.service;


import com.minlia.modules.rbac.backend.user.body.UserGarenRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserUpdateRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.body.UserCreateRequestBody;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    User create(UserCreateRequestBody entity);

    User update(UserUpdateRequestBody body);

    void delete(Long id);

    void grantRole(UserGarenRequestBody requestBody);

    void revokeRole(UserGarenRequestBody requestBody);

    Boolean locked(String guid);

    Boolean disabled(String guid);

}
