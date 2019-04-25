package com.minlia.module.email;

import com.minlia.module.email.property.AliyunEmailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by garen on 6/21/17.
 */
@Configuration
@EnableConfigurationProperties(value = {AliyunEmailProperties.class, MailProperties.class})
public class EmailAutoConfiguration {

    @Autowired
    private MailProperties mailProperties;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(null != mailProperties.getHost() ? mailProperties.getHost() : "smtp.gmail.com");
        mailSender.setPort(null != mailProperties.getPort() ? mailProperties.getPort() : 587);
        mailSender.setUsername(null != mailProperties.getUsername() ? mailProperties.getUsername() : "my.gmail@gmail.com");
        mailSender.setPassword(null != mailProperties.getPassword() ? mailProperties.getPassword() : "password");

        Properties props = mailSender.getJavaMailProperties();

        if (props.size() == 0) {
            props.put("mail.debug", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "true");
        } else {
            props.putAll(mailProperties.getProperties());
        }
        return mailSender;
    }

}
