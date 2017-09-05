package com.minlia.modules.rbac.service;

import com.minlia.modules.rbac.context.SecurityContextHolder;
import com.minlia.modules.rbac.domain.User;
import org.springframework.data.mybatis.support.GuidHolder;
import org.springframework.stereotype.Component;

/**
 * Created by will on 9/5/17.
 */
@Component
public class UserGuidAuditorHolder implements GuidHolder {

  @Override
  public String getGuid() {
    User user = SecurityContextHolder.getCurrentUser();
    if (null != user) {
      user.getGuid();
    } else {
      return DEFAULT_GUID_HOLDER_VALUE;
    }
    return DEFAULT_GUID_HOLDER_VALUE;
  }
}
