package com.minlia.modules.security.authentication.jwt.extractor;

public interface TokenExtractor {
    public String extract(String payload);
}
