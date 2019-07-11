package com.minlia.modules.security.exception;

import com.minlia.cloud.code.Code;
import org.springframework.security.core.AuthenticationException;

public class DefaultAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = -5959543783324224864L;

    private Code code;

    public DefaultAuthenticationException(Code code) {
        super(code.message());
        this.code = code;
    }

    public DefaultAuthenticationException(Code code, Throwable t) {
        super(code.message(), t);
        this.code = code;
    }

    public Code getCode() {
        return code;
    }

}
