package com.minlia.module.i18n;


import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 *
 */
@Configuration
//@ConditionalOnMissingBean(value =MessageSource.class, search = SearchStrategy.CURRENT)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class I18nAutoConfiguration {

}
