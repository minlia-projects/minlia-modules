package com.minlia.modules.security.authentication.jwt.extractor;

import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.security.code.SecurityCode;
import com.minlia.modules.security.exception.JwtAcceptableException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {
    public static String HEADER_PREFIX = "Bearer ";

    @Override
    public String extract(String header) {
//        ApiAssert.isNull(header, SecurityCode.Exception.JWT_ACCEPTABLE_NOT_NULL);
        if (StringUtils.isBlank(header)) {
            throw new JwtAcceptableException(SecurityCode.Exception.JWT_ACCEPTABLE_NOT_NULL.message());
        }

//        ApiAssert.state(!(header.length() < HEADER_PREFIX.length()), SecurityCode.Exception.JWT_ACCEPTABLE_INVALID_SIZE);
        if (header.length() < HEADER_PREFIX.length()) {
            throw new JwtAcceptableException(SecurityCode.Exception.JWT_ACCEPTABLE_INVALID_SIZE.message());
        }
        return header.substring(HEADER_PREFIX.length(), header.length());
    }
}
