package com.minlia.module.swagger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Swagger 配置参数
 *
 * @author garen
 * @version 2.0
 * @date 2020-11-2
 */
@Data
@Component
@ConfigurationProperties(prefix = "swagger", ignoreUnknownFields = false)
public class SwaggerProperties {

    private Boolean enable = true;

    private String host = "http://localhost:8080";

    private String title = "Minlia";

    private String version = "1.0";

    private String path = "/api/.*";

    private String description = "Minlia Api Documentations";

    /**
     * 服务条款
     */
    private String termsOfServiceUrl = "https://www.google.com/tos";

    boolean redirect = false;

    public Contact contact;

    @Data
    public static class Contact {
        String name;
        String url;
        String email;
    }

}