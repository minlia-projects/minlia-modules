package com.minlia.module.email;

import com.minlia.module.email.config.EmailConfig;
import org.apache.commons.collections.MapUtils;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Properties;

/**
 * Created by garen on 6/21/17.
 */
@EnableAsync
@Configuration
@EnableConfigurationProperties(value = {MailProperties.class})
public class EmailAutoConfiguration {

    private final EmailConfig emailConfig;
    private final MailProperties mailProperties;

    public EmailAutoConfiguration(EmailConfig emailConfig, MailProperties mailProperties) {
        this.emailConfig = emailConfig;
        this.mailProperties = mailProperties;
    }

//    @Bean
//    public JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(null != emailConfig.getHost() ? emailConfig.getHost() : "smtp.gmail.com");
//        mailSender.setPort(null != emailConfig.getPort() ? emailConfig.getPort() : 587);
//
//        Properties props = mailSender.getJavaMailProperties();
//        if (MapUtils.isEmpty(mailProperties.getProperties())) {
//            props.put("mail.debug", "true");
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.ssl.enable", "true");
//            props.put("mail.smtp.starttls.enable", "true");
//            props.put("mail.smtp.starttls.required", "true");
//        } else {
//            props.putAll(mailProperties.getProperties());
//        }
//
//        if (props.getProperty("mail.smtp.auth", "false").equals("true")) {
//            mailSender.setUsername(emailConfig.getUsername());
//            mailSender.setPassword(emailConfig.getPassword());
//        }
//
//        return mailSender;
//    }

}
