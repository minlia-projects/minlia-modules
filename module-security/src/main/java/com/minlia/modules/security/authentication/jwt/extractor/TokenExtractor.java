package com.minlia.modules.security.authentication.jwt.extractor;

public interface TokenExtractor {
    String extract(String payload);
}
