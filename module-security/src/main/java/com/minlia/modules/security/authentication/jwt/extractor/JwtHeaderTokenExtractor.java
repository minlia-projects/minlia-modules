package com.minlia.modules.security.authentication.jwt.extractor;

import com.minlia.modules.security.exception.JwtAcceptableException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {
    public static String HEADER_PREFIX = "Bearer ";

    @Override
    public String extract(String header) {
//        ApiPreconditions.checkNotNull(header, ApiCode.ACCESS_TOKEN_INVALID,"Authorization header cannot be blank!");
        if (StringUtils.isEmpty(header)) {
            throw new JwtAcceptableException("Authorization header cannot be blank!");
        }

//        ApiPreconditions.is(header.length() < HEADER_PREFIX.length(), ApiCode.ACCESS_TOKEN_INVALID,"Invalid authorization header size.");
        if (header.length() < HEADER_PREFIX.length()) {
            throw new JwtAcceptableException("Invalid authorization header size.");
        }

        return header.substring(HEADER_PREFIX.length(), header.length());
    }
}
