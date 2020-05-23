package com.minlia.modules.security.authentication.service;

import org.springframework.security.core.Authentication;

/**
 * Created by will on 20180802.
 *
 * @author will
 * @author garen
 */
public interface AuthenticationService {

    /**
     * 验证用户
     * 参考 AjaxAuthenticationProvider 实现
     *
     * @param authentication
     * @return
     */
//    boolean authentication(Authentication authentication);
    Authentication authentication(Authentication authentication);

//    /**
//     * 检索用户
//     *
//     * @param username
//     * @param usernamePasswordAuthenticationToken
//     * @return
//     */
//    UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken);

}