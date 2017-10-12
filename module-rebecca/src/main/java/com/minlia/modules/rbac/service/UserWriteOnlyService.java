package com.minlia.modules.rbac.service;


import com.minlia.cloud.service.WriteOnlyService;
import com.minlia.modules.rbac.dao.UserDao;
import com.minlia.modules.rbac.domain.User;
import com.minlia.modules.rbac.v1.backend.user.body.UserGarentRoleRequestBody;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 8/14/17.
 */
@Transactional(readOnly = false)
public interface UserWriteOnlyService extends WriteOnlyService<UserDao, User, Long> {



  /**
   * 为用户授权
   */
  User grantRole(UserGarentRoleRequestBody body);

  /**
   * 回收用户权限
   */
  User revokeRole(UserGarentRoleRequestBody body);


}
