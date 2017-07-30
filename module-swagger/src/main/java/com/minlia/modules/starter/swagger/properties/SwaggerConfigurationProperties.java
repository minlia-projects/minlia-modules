 package com.minlia.modules.starter.swagger.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("swagger")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwaggerConfigurationProperties {

  String title = "APPLICATION.NAME";

  String version = "APPLICATION.VERSION";

  String path="/api/.*";
  String description="Minlia Cloud Api Documentations";
  String contact="cloud@minlia.com";

  boolean redirect = false;

}
