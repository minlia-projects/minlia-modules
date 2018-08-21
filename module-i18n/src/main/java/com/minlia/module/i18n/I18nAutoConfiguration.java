package com.minlia.module.i18n;


import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 *
 */
@Configuration
//@ConditionalOnMissingBean(value =MessageSource.class, search = SearchStrategy.CURRENT)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class I18nAutoConfiguration {

    @Bean
    public Mapper mapper() {
        return new DozerBeanMapper() ;
    }

}
