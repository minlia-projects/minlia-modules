package com.minlia.modules.security.body;

import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class AuthenticationErrorResponseBody {

    // HTTP Response Status Code
    private final HttpStatus status;

    // Error code
    private final String code;

    // General Error message
    private final String message;

    private long lockTime;

    private int failureTimes;

    private final LocalDateTime timestamp;

    public AuthenticationErrorResponseBody(HttpStatus status, final Code code) {
        this.status = status;
        this.code = code.code();
        this.message = Lang.get(code.i18nKey());
        this.timestamp = LocalDateTime.now();
    }

    public AuthenticationErrorResponseBody(HttpStatus status, final Code code, final Long lockTime) {

        Object[] o = new Object[]{lockTime};

        this.status = status;
        this.code = code.code();
        this.message = Lang.get(code.i18nKey(), new Object[]{lockTime});
        this.lockTime = lockTime;
        this.timestamp = LocalDateTime.now();
    }

    public AuthenticationErrorResponseBody(HttpStatus status, final Code code, final Integer failureTimes) {
        this.status = status;
        this.code = code.code();
        this.message = Lang.get(code.i18nKey(), new Object[]{failureTimes});
        this.failureTimes = failureTimes;
        this.timestamp = LocalDateTime.now();
    }

}
