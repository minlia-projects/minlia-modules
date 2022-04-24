package com.minlia.modules.security.authentication.jwt.extractor;

import com.minlia.modules.security.code.SecurityCode;
import com.minlia.modules.security.exception.JwtAcceptableException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 获取头部TOKEN
 *
 * @author garen
 */
@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {

    public static String HEADER_PREFIX = "Bearer ";

    @Override
    public String extract(String header) {
        if (StringUtils.isBlank(header)) {
            throw new JwtAcceptableException(SecurityCode.Exception.JWT_TOKEN_NOT_NULL.message());
        }
        if (header.length() < HEADER_PREFIX.length()) {
            throw new JwtAcceptableException(SecurityCode.Exception.JWT_TOKEN_INVALID.message());
        }
        return header.substring(HEADER_PREFIX.length());
    }

}