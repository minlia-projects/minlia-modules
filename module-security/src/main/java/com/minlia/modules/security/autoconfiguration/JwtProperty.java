package com.minlia.modules.security.autoconfiguration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author garen
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "system.security.jwt")
public class JwtProperty {

    /**
     * Token issuer.
     */
    private String tokenIssuer;

    /**
     * Key is used ro sign
     */
    private String tokenSigningKey;

    /**
     * can be refreshed during this timeframe.
     */
    private Integer refreshTokenExpTime;

    /**
     * will expire after this time.
     */
    private Integer tokenExpirationTime;

}