package com.minlia.module.email.holder;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import com.minlia.module.email.entity.EmailSenderEntity;
import com.minlia.module.email.service.EmailSenderService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * @author garen
 */
@Slf4j
@Component
public class EmailSenderHolder {

    private final Map<String, MailSender> mailSender = Maps.newHashMap();
    private static String DEFAULT_SENDER = "DEFAULT";

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private EmailSenderService emailSenderService;

    /**
     * 初始化 sender
     */
    @PostConstruct
    public void buildMailSender() {
        log.info("初始化 mail sender");
        List<EmailSenderEntity> senderEntities = emailSenderService.list(Wrappers.<EmailSenderEntity>lambdaQuery().eq(EmailSenderEntity::getDisFlag, false));
        senderEntities.forEach(this::builderMailSender);
    }

    private void builderMailSender(EmailSenderEntity senderEntity) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setDefaultEncoding("UTF-8");
        javaMailSender.setHost(senderEntity.getHost());
//        javaMailSender.setPort(senderEntity.getPort());
        javaMailSender.setUsername(senderEntity.getUsername());
        javaMailSender.setPassword(senderEntity.getPassword());
//        javaMailSender.setProtocol(senderEntity.getProtocol());

        Properties props = javaMailSender.getJavaMailProperties();
        if (MapUtils.isEmpty(mailProperties.getProperties())) {
            props.put("mail.debug", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "true");
        } else {
            props.putAll(mailProperties.getProperties());
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }
        javaMailSender.setJavaMailProperties(props);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessage.setFrom(senderEntity.getUsername());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.put(senderEntity.getCode(), MailSender.builder().javaMailSender(javaMailSender).mimeMessage(mimeMessage).replyTo(senderEntity.getReplyTo()).build());
        if (senderEntity.getDefFlag()) {
            DEFAULT_SENDER = senderEntity.getCode();
        }
    }

    public MailSender get() {
        return mailSender.get(DEFAULT_SENDER);
    }

    public MailSender get(String code) {
        return Objects.isNull(code) ? get() : mailSender.get(code);
    }

    public void add(EmailSenderEntity senderEntity) {
        builderMailSender(senderEntity);
    }

    public void remove(String code) {
        mailSender.remove(code);
    }

    public void clear() {
        mailSender.clear();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MailSender {
        private JavaMailSender javaMailSender;

        private MimeMessage mimeMessage;

        private String replyTo;

        public void send() {
            javaMailSender.send(mimeMessage);
        }
    }

}