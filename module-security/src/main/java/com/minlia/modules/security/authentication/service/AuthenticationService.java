package com.minlia.modules.security.authentication.service;

import org.springframework.security.core.Authentication;

/**
 * Created by will on 8/14/17.
 */
public interface AuthenticationService {

    /**
     * 验证用户
     * 参考 AjaxAuthenticationProvider 实现
     *
     * @param authentication
     * @return
     */
    public Authentication authentication(Authentication authentication);

}
