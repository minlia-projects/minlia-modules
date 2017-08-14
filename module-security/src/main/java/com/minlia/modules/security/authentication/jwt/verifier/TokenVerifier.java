package com.minlia.modules.security.authentication.jwt.verifier;

public interface TokenVerifier {
    public boolean verify(String jti);
}
