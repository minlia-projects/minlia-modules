package com.minlia.modules.security.body;

import com.minlia.cloud.i18n.Lang;
import com.minlia.modules.security.code.AuthenticationErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class AuthenticationErrorResponseBody {

    // HTTP Response Status Code
    private final HttpStatus status;

    // Error code
    private final AuthenticationErrorCode code;

    // General Error message
    private final String message;

    private long lockTime;

    private int failureTimes;

    private final Date timestamp;

    public AuthenticationErrorResponseBody(HttpStatus status, final AuthenticationErrorCode code) {
        this.status = status;
        this.code = code;
        this.message = code.getCode();
        this.timestamp = new java.util.Date();
    }

    public AuthenticationErrorResponseBody(HttpStatus status, final AuthenticationErrorCode code, final String message) {
        this.status = status;
        this.code = code;
        this.message = Lang.get(message);
        this.timestamp = new java.util.Date();
    }

    public AuthenticationErrorResponseBody(HttpStatus status, final AuthenticationErrorCode code, final String message,final Long lockTime) {
        this.message = message;
        this.code = code;
        this.status = status;
        this.lockTime = lockTime;
        this.timestamp = new java.util.Date();
    }

    public AuthenticationErrorResponseBody(HttpStatus status, final AuthenticationErrorCode code, final String message,final Integer failureTimes) {
        this.message = message;
        this.code = code;
        this.status = status;
        this.failureTimes = failureTimes;
        this.timestamp = new java.util.Date();
    }

    public Integer getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }

    public AuthenticationErrorCode getCode() {
        return code;
    }

    public Long getLockTime() {
        return lockTime;
    }

    public Integer getFailureTimes() {
        return failureTimes;
    }

    public Date getTimestamp() {
        return timestamp;
    }

}
