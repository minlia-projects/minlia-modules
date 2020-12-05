package com.minlia.module.swagger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Swagger 配置参数
 *
 * @author garen
 * @version 2.0
 * @date 2020-11-2
 */
@ConfigurationProperties(prefix = "swagger", ignoreUnknownFields = false)
@Data
public class SwaggerProperties {

    private String title = "GAUSS";

    private String version = "1.0";

    private String path = "/api/.*";

    private String description = "TP Api Documentations";

    private String author = "garen";

    private String url = "https://www.google.com";

    private String email = "191285052@qq.com";

    /**
     * 服务条款
     */
    private String termsOfServiceUrl = "https://www.google.com/tos";

    boolean redirect = false;

}