package com.minlia.modules.security.exception;

import com.minlia.modules.security.model.token.JwtToken;
import org.springframework.security.core.AuthenticationException;

public class AjaxLockedException extends AuthenticationException {
    private static final long serialVersionUID = -5959543783324224864L;

    private JwtToken token;

    private Long lockTime;

    public AjaxLockedException(String msg) {
        super(msg);
    }

    public AjaxLockedException(String msg,Long lickTime) {
        super(msg);
        this.lockTime = lickTime;
    }

    public AjaxLockedException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }

    public Long getLockTime() {
        return lockTime;
    }

}
