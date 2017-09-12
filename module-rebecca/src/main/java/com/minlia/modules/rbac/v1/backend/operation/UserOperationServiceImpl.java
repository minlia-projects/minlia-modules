package com.minlia.modules.rbac.v1.backend.operation;

import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.modules.rbac.domain.User;
import com.minlia.modules.rbac.service.UserReadOnlyService;
import com.minlia.modules.rbac.service.UserWriteOnlyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by will on 9/8/17.
 */
@Service
@Slf4j
public class UserOperationServiceImpl implements UserOperationService {

  @Autowired
  UserWriteOnlyService userWriteOnlyService;

  @Autowired
  UserReadOnlyService userReadOnlyService;

  @Override
  public Boolean freeze(Long id) {
    User user=userReadOnlyService.findOne(id);
    ApiPreconditions.checkNotNull(user, ApiCode.NOT_NULL);
    user.setLocked(Boolean.TRUE);
    userWriteOnlyService.update(user);
    return Boolean.TRUE;
  }


  @Override
  public Boolean unfreeze(Long id) {
    User user=userReadOnlyService.findOne(id);
    ApiPreconditions.checkNotNull(user, ApiCode.NOT_NULL);
    user.setLocked(Boolean.FALSE);
    userWriteOnlyService.update(user);
    return Boolean.TRUE;
  }


}
