package com.minlia.module.approved;

import com.minlia.module.approved.email.property.AliyunEmailProperties;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@EnableConfigurationProperties(value = {AliyunEmailProperties.class, MailProperties.class})
public class ApprovedEmailAutoConfiguration {

    @Autowired
    private MailProperties mailProperties;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(null != mailProperties.getHost() ? mailProperties.getHost() : "smtp.gmail.com");
        mailSender.setPort(null != mailProperties.getPort() ? mailProperties.getPort() : 587);

        Properties props = mailSender.getJavaMailProperties();
        if (MapUtils.isEmpty(mailProperties.getProperties())) {
            props.put("mail.debug", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "true");
        } else {
            props.putAll(mailProperties.getProperties());
        }

        if (props.getProperty("mail.smtp.auth", "false").equals("true")) {
            mailSender.setUsername(mailProperties.getUsername());
            mailSender.setPassword(mailProperties.getPassword());
        }
        return mailSender;
    }

}
