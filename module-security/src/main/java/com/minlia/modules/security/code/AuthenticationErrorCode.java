package com.minlia.modules.security.code;

import com.fasterxml.jackson.annotation.JsonValue;
import com.minlia.cloud.code.ApiCode;

public enum AuthenticationErrorCode {

    AUTHENTICATION(ApiCode.INVALID_CREDENTIAL),

    JWT_TOKEN_EXPIRED(ApiCode.ACCESS_TOKEN_HAS_EXPIRED);
    
    private String code;

    AuthenticationErrorCode(Integer code) {
        this.code = code+"";
    }

    @JsonValue
    public String getCode() {
        return code;
    }

}
