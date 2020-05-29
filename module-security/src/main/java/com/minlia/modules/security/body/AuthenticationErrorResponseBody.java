package com.minlia.modules.security.body;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;
import com.minlia.module.common.constant.LocalDateConstants;
import lombok.Data;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Locale;

@Data
public class AuthenticationErrorResponseBody {

    // HTTP Response Status Code
//    private final HttpStatus status;
    private final Integer status;

    private final String code;

    private final String message;

    private long lockTime;

    private int failureTimes;

    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)
    private final LocalDateTime timestamp;

    public AuthenticationErrorResponseBody(HttpStatus status, final Code code) {
        this.status = status.value();
        this.code = code.code();
//        this.message = code.message();
        this.message = Lang.get(code.i18nKey(), null, getLocale());
        this.timestamp = LocalDateTime.now();
    }

    public AuthenticationErrorResponseBody(HttpStatus status, final Code code, final Long lockTime) {
        this.status = status.value();
        this.code = code.code();
//        this.message = code.message(new Object[]{lockTime});
        this.message = Lang.get(code.i18nKey(), new Object[]{lockTime}, getLocale());
        this.lockTime = lockTime;
        this.timestamp = LocalDateTime.now();
    }

    public AuthenticationErrorResponseBody(HttpStatus status, final Code code, final Integer failureTimes) {
        this.status = status.value();
        this.code = code.code();
//        this.message = code.message(new Object[]{failureTimes});
        this.message = Lang.get(code.i18nKey(), new Object[]{failureTimes}, getLocale());
        this.failureTimes = failureTimes;
        this.timestamp = LocalDateTime.now();

    }

    public AuthenticationErrorResponseBody(final Code code) {
        this.status = HttpStatus.UNAUTHORIZED.value();
        this.code = code.code();
//        this.message = code.message();
        this.message = Lang.get(code.i18nKey(), null, getLocale());
        this.timestamp = LocalDateTime.now();
    }

    public AuthenticationErrorResponseBody(final Code code, final Object... args) {
        this.status = HttpStatus.UNAUTHORIZED.value();
        this.code = code.code();
//        this.message = code.message(args);
        this.message = Lang.get(code.i18nKey(), args, getLocale());
        this.timestamp = LocalDateTime.now();
    }

    public AuthenticationErrorResponseBody(final Code code, final Long lockTime) {
        this.status = HttpStatus.UNAUTHORIZED.value();
        this.code = code.code();
//        this.message = code.message(new Object[]{lockTime});
        this.message = Lang.get(code.i18nKey(), new Object[]{lockTime}, getLocale());
        this.lockTime = lockTime;
        this.timestamp = LocalDateTime.now();
    }

    public AuthenticationErrorResponseBody(final Code code, final Integer failureTimes) {
        this.status = HttpStatus.UNAUTHORIZED.value();
        this.code = code.code();
//        this.message = code.message(new Object[]{failureTimes});
        this.message = Lang.get(code.i18nKey(), new Object[]{failureTimes}, getLocale());
        this.failureTimes = failureTimes;
        this.timestamp = LocalDateTime.now();
    }

    public static Locale getLocale() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Cookie localeCookie = WebUtils.getCookie(request, "locale");
//        return null == localeCookie ? LocaleContextHolder.getLocale() : LocaleUtils.toLocale(localeCookie.getValue());
        return null == localeCookie ? LocaleContextHolder.getLocale() : StringUtils.parseLocale(localeCookie.getValue());
    }

}
