package com.minlia.module.language.v1;

import static com.minlia.cloud.constant.Constants.EXCEPTIONS_APICODE_PREFIX;

import com.google.common.collect.Lists;
import com.minlia.module.language.v1.messagesource.InitializableMessageSource;
import com.minlia.module.language.v1.messagesource.jdbc.JdbcMessageProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Locale;

/**
 * Created by will on 6/19/17.
 * 启动时延迟此BEAN初始化
 */
@Configuration
public class LanguageAutoConfiguration {


    @Configuration
    @Import(LocaleConfiguration.class)
    @ConditionalOnMissingBean(LocaleConfiguration.class)
    public static class MinliaLocaleConfiguration implements  EnvironmentAware {


        @Autowired
        private DataSource dataSource;


        private RelaxedPropertyResolver propertyResolver;


        @Lazy
        @Bean("messageSource")
        public InitializableMessageSource initializableMessageSource() {

            String basename=fallback();
            Locale defaultLocale = new Locale("zh", "CN");
            InitializableMessageSource messageSource = new InitializableMessageSource();
            messageSource.setDefaultLocale(defaultLocale);
            messageSource.setBasename(basename);
            messageSource.setBasenames(Lists.newArrayList(basename, "ValidationMessages","Messages"));
            messageSource.setUseCodeAsDefaultMessage(true);
            messageSource.setMessageProvider(jdbcMessageProvider());
            messageSource.setAutoInitialize(false);
            return messageSource;
        }

        private String fallback(){
            String fallbackBasename = EXCEPTIONS_APICODE_PREFIX;
            return fallbackBasename;
//            String basename="";
//            if(!StringUtils.isEmpty(propertyResolver.getProperty("name"))){
//                basename=propertyResolver.getProperty("name");
//            }else{
//                basename=fallbackBasename;
//            }
//            return basename;
        }

        @Lazy
        @Bean
        public JdbcMessageProvider jdbcMessageProvider() {
            JdbcMessageProvider jdbcMessageProvider = new JdbcMessageProvider();
            jdbcMessageProvider.setDataSource(dataSource);
            jdbcMessageProvider.setTableName("mdl_language");
            return jdbcMessageProvider;
        }


        @Override
        public void setEnvironment(Environment environment) {
            this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.application.");
        }
    }

}
