package com.minlia.modules.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * jwt过期异常
 *
 * @author garen
 */
public class JwtExpiredTokenException extends AuthenticationException {

    private static final long serialVersionUID = -5959543783324224864L;

    public JwtExpiredTokenException(String msg) {
        super(msg);
    }

    public JwtExpiredTokenException(String msg, Throwable t) {
        super(msg, t);
    }

}