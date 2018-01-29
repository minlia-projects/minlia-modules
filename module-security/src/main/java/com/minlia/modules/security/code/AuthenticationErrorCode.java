package com.minlia.modules.security.code;

import com.fasterxml.jackson.annotation.JsonValue;
import com.minlia.cloud.code.ApiCode;

/**
 * Enumeration of REST Error types.
 * 
 * @author vladimir.stankovic
 *
 *         Aug 3, 2016
 */
public enum AuthenticationErrorCode {
    GLOBAL(2),

    AUTHENTICATION(ApiCode.INVALID_CREDENTIAL), JWT_TOKEN_EXPIRED(ApiCode.ACCESS_TOKEN_HAS_EXPIRED);
    
    private int errorCode;

    private AuthenticationErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
