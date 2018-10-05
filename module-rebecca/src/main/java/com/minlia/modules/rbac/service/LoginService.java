package com.minlia.modules.rbac.service;

import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.security.model.UserContext;

import java.util.HashMap;

public interface LoginService {

    UserContext getUserContext(User user, String currrole);

    HashMap getLoginInfoByUser(User user, String currrole);

}
