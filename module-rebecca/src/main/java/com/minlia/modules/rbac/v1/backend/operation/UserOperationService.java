package com.minlia.modules.rbac.v1.backend.operation;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 9/8/17.
 */
@Transactional(readOnly = false)
public interface UserOperationService {
  public static final String USER_OPERATION_FREEZE="user.operation.freeze";
  public static final String USER_OPERATION_UNFREEZE="user.operation.unfreeze";

  Boolean freeze(Long id);

  Boolean unfreeze(Long id);

}
