package com.minlia.modules.security.exception;

import org.springframework.security.core.AuthenticationException;

public class BadAccountPatternPaException extends AuthenticationException {

    public BadAccountPatternPaException(String msg) {
        super(msg);
    }

    public BadAccountPatternPaException(String msg, Throwable t) {
        super(msg, t);
    }

}
