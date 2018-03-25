package com.minlia.module.language.v2;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "minlia.i18n")
public class I18nProperties {

    private Long cachedMilliSecond=2073600000L;

    @NotNull
    private String i18nTableName="language";

}
