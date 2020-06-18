package com.minlia.modules.rebecca.service;

import com.minlia.modules.rebecca.bean.domain.User;
import com.minlia.modules.rebecca.body.UserLogonResponseBody;
import com.minlia.modules.security.model.UserContext;

import java.util.HashMap;

public interface LoginService {

    UserContext getUserContext(User user, String currrole);

    HashMap getLoginInfoByUser(User user, String currrole);


    UserLogonResponseBody loginWithRole(User user, String currentRole);

}
