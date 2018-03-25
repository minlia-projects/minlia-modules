package com.minlia.module.language.v2;


import com.minlia.module.language.v1.LocaleConfiguration;
import java.util.Locale;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;

/**
 * Created by will on 6/19/17. 启动时延迟此BEAN初始化
 */
@Configuration
@EnableConfigurationProperties(I18nProperties.class)
@ConditionalOnProperty(prefix = "minlia.i18n", name = {"cachedMilliSecond", "i18nTableName"})
public class LanguageAutoConfiguration {

  @Configuration
  @Import(LocaleConfiguration.class)
  public static class MinliaLocaleConfiguration implements EnvironmentAware {

    @Autowired
    private DataSource dataSource;


    @Autowired
    private  I18nProperties i18nProperties;

    @Bean
    @ConditionalOnMissingBean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate()
        throws Exception {
      return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
//    @ConditionalOnMissingBean
    public MessageSource messageSource() throws Exception {
      JdbcMessageSource messageSource = new JdbcMessageSource();
      messageSource
          .setNamedParameterJdbcOperations(namedParameterJdbcTemplate());
      messageSource.setUseCodeAsDefaultMessage(false);
      messageSource
          .setSqlStatement(
              "select message from "+i18nProperties.getI18nTableName()+" where code = ? and language = ? and country = ? limit 1");
      messageSource.setCachedMilliSecond(i18nProperties.getCachedMilliSecond());//long最长支持24天的毫秒数, 内部实现的缓存
      messageSource.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
      return messageSource;
    }

    @Lazy
    @Bean(name = "localeResolver")
//    @ConditionalOnMissingBean
    public LocaleResolver localeResolver() {
      AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
      localeResolver.setDefaultLocale(Locale.getDefault());
      return localeResolver;
    }

    @Bean
//    @ConditionalOnMissingBean
    public LocalValidatorFactoryBean validator() throws Exception {
      LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
      bean.setValidationMessageSource(messageSource());
      return bean;
    }


    @Override
    public void setEnvironment(Environment environment) {
//            this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.application.");
    }
  }

}
