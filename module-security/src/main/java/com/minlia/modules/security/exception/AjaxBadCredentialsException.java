package com.minlia.modules.security.exception;

import com.minlia.modules.security.model.token.JwtToken;
import org.springframework.security.core.AuthenticationException;

public class AjaxBadCredentialsException extends AuthenticationException {
    private static final long serialVersionUID = -5959543783324224864L;

    private JwtToken token;

    private Integer failureTimes;

    public AjaxBadCredentialsException(String msg) {
        super(msg);
    }

    public AjaxBadCredentialsException(String msg, Integer failureTimes) {
        super(msg);
        this.failureTimes = failureTimes;
    }

    public AjaxBadCredentialsException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }

    public Integer getFailureTimes() {
        return failureTimes;
    }

}
