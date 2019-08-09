package com.minlia.modules.security.exception;

import com.minlia.cloud.code.Code;
import org.springframework.security.core.AuthenticationException;

public class DefaultAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = -5959543783324224864L;

    private Code code;

    private Object[] args;

    public DefaultAuthenticationException(Code code) {
        super(code.message());
        this.code = code;
    }

    public DefaultAuthenticationException(Code code, Object... args) {
        super(code.message());
        this.code = code;
        this.args = args;
    }

    public DefaultAuthenticationException(Throwable t, Code code) {
        super(code.message(), t);
        this.code = code;
    }

    public DefaultAuthenticationException(Throwable t, Code code, Object... args) {
        super(code.message(), t);
        this.code = code;
        this.args = args;
    }

    public Code getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }
}
