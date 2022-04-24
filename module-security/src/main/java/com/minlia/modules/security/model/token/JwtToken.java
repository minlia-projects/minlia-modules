package com.minlia.modules.security.model.token;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author garen
 */
@Data
@AllArgsConstructor
public final class JwtToken {

    private final String token;

    private Claims claims;

}