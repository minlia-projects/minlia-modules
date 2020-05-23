//package com.minlia.modules.otp;
//
//import com.minlia.modules.otp.sms.OtpSmsProperties;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.context.properties.bind.BindResult;
//import org.springframework.boot.context.properties.bind.Binder;
//import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
//import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//import java.util.Properties;
//
//@Slf4j
//@Configuration
//@EnableConfigurationProperties(value = {OtpSmsProperties.class})
//public class OtpAutoConfiguration implements EnvironmentAware {
//
//    private Properties properties;
//
//    @Override
//    public void setEnvironment(Environment environment) {
//        Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(environment);
//        Binder binder = new Binder(sources);
//        BindResult<Properties> bindResult = binder.bind("system.sms.otp", Properties.class);
//        Properties properties = bindResult.get();
//        this.properties = properties;
//    }
//
//}