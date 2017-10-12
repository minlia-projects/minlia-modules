package com.minlia.modules.rbac.service;


import com.minlia.cloud.service.JpaWriteOnlyService;
import com.minlia.cloud.service.WriteOnlyService;
import com.minlia.modules.rbac.dao.UserDao;
import com.minlia.modules.rbac.domain.User;
import com.minlia.modules.rbac.repository.UserRepository;
import com.minlia.modules.rbac.v1.backend.user.body.UserGarentRoleRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 8/14/17.
 */
@Transactional(readOnly = false)
public interface UserJpaWriteOnlyService extends JpaWriteOnlyService<UserRepository, User, Long> {




}
