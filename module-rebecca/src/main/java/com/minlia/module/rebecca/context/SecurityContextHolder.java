package com.minlia.module.rebecca.context;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.service.SysUserService;
import com.minlia.modules.security.context.MinliaSecurityContextHolder;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for Spring Security.
 *
 * @author garen
 */
@Slf4j
public final class SecurityContextHolder extends MinliaSecurityContextHolder {

    public static SysUserEntity getCurrentUser() {
        Long uid = getUid();
        if (null != uid) {
            return ContextHolder.getContext().getBean(SysUserService.class).getById(uid);
        } else {
            return null;
        }
    }

}