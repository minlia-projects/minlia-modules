package com.minlia.modules.security.authentication.jwt.extractor;

import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {
    public static String HEADER_PREFIX = "Bearer ";

    @Override
    public String extract(String header) {
        ApiPreconditions.checkNotNull(header, ApiCode.ACCESS_TOKEN_INVALID);
//        if (StringUtils.isEmpty(header)) {
//            throw new AuthenticationServiceException("Authorization header cannot be blank!");
//        }

        ApiPreconditions.is(header.length() < HEADER_PREFIX.length(), ApiCode.ACCESS_TOKEN_INVALID);
//        if (header.length() < HEADER_PREFIX.length()) {
//            throw new AuthenticationServiceException("Invalid authorization header size.");
//        }

        return header.substring(HEADER_PREFIX.length(), header.length());
    }
}
