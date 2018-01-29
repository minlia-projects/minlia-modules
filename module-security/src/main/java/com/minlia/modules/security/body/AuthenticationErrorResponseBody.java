package com.minlia.modules.security.body;

import com.minlia.modules.security.code.AuthenticationErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class AuthenticationErrorResponseBody {
    // HTTP Response Status Code
    private final HttpStatus status;

    // General Error message
    private final String message;

    // Error code
    private final AuthenticationErrorCode code;

    private final Date timestamp;

    public AuthenticationErrorResponseBody(HttpStatus status, final AuthenticationErrorCode code, final String message) {
        this.message = message;
        this.code = code;
        this.status = status;
//        this.path = path;
//        this.exception = exception;
        this.timestamp = new java.util.Date();
    }


    protected AuthenticationErrorResponseBody(final String message, final AuthenticationErrorCode code, HttpStatus status) {
        this.message = message;
        this.code = code;
        this.status = status;
        this.timestamp = new Date();
    }

    public static AuthenticationErrorResponseBody of(final String message, final AuthenticationErrorCode authenticationErrorCode, HttpStatus status) {
        return new AuthenticationErrorResponseBody(message, authenticationErrorCode, status);
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

    public Date getTimestamp() {
        return timestamp;
    }
}
