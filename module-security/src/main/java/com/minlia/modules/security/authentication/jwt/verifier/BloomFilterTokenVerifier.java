package com.minlia.modules.security.authentication.jwt.verifier;

import org.springframework.stereotype.Component;

@Component
public class BloomFilterTokenVerifier implements TokenVerifier {
    @Override
    public boolean verify(String jti) {
        return true;
    }
}
