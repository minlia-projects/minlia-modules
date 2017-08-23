package com.minlia.modules.rbac.service;

import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.service.AbstractWriteOnlyService;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.modules.rbac.dao.UserDao;
import com.minlia.modules.rbac.domain.Role;
import com.minlia.modules.rbac.domain.User;
import com.minlia.modules.rbac.endpoint.body.UserGarentRoleRequestBody;
import com.minlia.modules.rbac.repository.RoleRepository;
import com.minlia.modules.rbac.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/14/17.
 */
@Service
public class UserServiceImpl extends AbstractWriteOnlyService<UserDao, User, Long> implements
    UserService {

  @Autowired
  private UserDao userDao;
  @Autowired
  private UserRepository repository;

  @Autowired
  private RoleRepository roleRepository;


  @Override
  public User grantRole(UserGarentRoleRequestBody requestBody) {
    User user = repository.findOne(requestBody.getUserId());
    ApiPreconditions.is(null == user, ApiCode.NOT_FOUND);
    Role role = roleRepository.findOneByCode(requestBody.getRoleCode());
    ApiPreconditions.is(null == role, ApiCode.NOT_FOUND);
    user.getRoles().add(role);
    return super.update(user);
  }

  @Override
  public User revokeRole(UserGarentRoleRequestBody requestBody) {
    User user = repository.findOne(requestBody.getUserId());
    ApiPreconditions.is(null == user, ApiCode.NOT_FOUND);
    Role role = roleRepository.findOneByCode(requestBody.getRoleCode());
    ApiPreconditions.is(null == role, ApiCode.NOT_FOUND);
    user.getRoles().remove(role);
    return super.update(user);
  }
}
