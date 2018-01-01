package com.minlia.module.language.v1.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "minlia.i18n")
@Data
public class I18nProperties {

  private Boolean enableI18nAnnotationScan;

}
