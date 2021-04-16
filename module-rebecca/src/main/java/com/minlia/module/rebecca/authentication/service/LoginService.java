package com.minlia.module.rebecca.authentication.service;

import com.minlia.module.rebecca.authentication.bean.UserLogonResponseBody;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.modules.security.model.UserContext;

import java.util.Map;


public interface LoginService {

    UserContext getUserContext(SysUserEntity entity, String currrole);

    UserLogonResponseBody loginWithRole(SysUserEntity entity, String currentRole);

    Map getLoginInfoByUid(Long uid);

    Map getLoginInfoByCellphone(String cellphone);

}