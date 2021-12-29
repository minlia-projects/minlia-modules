package com.minlia.module.email.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.minlia.cloud.constant.SymbolConstants;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.email.config.EmailConfig;
import com.minlia.module.email.entity.EmailRecordEntity;
import com.minlia.module.email.holder.EmailSenderHolder;
import com.minlia.module.email.service.EmailRecordService;
import com.minlia.module.email.service.EmailService;
import com.minlia.module.email.util.TextReplaceUtils;
import com.minlia.module.i18n.enums.LocaleEnum;
import com.minlia.module.i18n.util.LocaleUtils;
import com.minlia.module.richtext.constant.RichtextCode;
import com.minlia.module.richtext.entity.RichtextEntity;
import com.minlia.module.richtext.enumeration.RichtextTypeEnum;
import com.minlia.module.richtext.service.RichtextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;
import java.util.Objects;

/**
 * @author garen
 * @date 2018/8/10
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final EmailConfig emailConfig;
    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;
    private final TemplateEngine templateEngine;
    private final RichtextService richtextService;
    private final EmailRecordService emailRecordService;
    private final EmailSenderHolder emailSenderHolder;

    public EmailServiceImpl(EmailConfig emailConfig, JavaMailSender mailSender, MailProperties mailProperties, TemplateEngine templateEngine, RichtextService richtextService, EmailRecordService emailRecordService, EmailSenderHolder emailSenderHolder) {
        this.emailConfig = emailConfig;
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
        this.templateEngine = templateEngine;
        this.richtextService = richtextService;
        this.emailSenderHolder = emailSenderHolder;
        this.emailRecordService = emailRecordService;
    }

    @Override
    public EmailRecordEntity sendRichtextMail(String[] to, String templateCode, Map<String, Object> variables) {
        return this.sendRichtextMail(null, to, templateCode, variables);
    }

    @Override
    public EmailRecordEntity sendRichtextMail(String sender, String[] to, String templateCode, Map<String, Object> variables) {
        return this.sendRichtextMail(sender, to, templateCode, variables, LocaleUtils.locale());
    }

    @Override
    public EmailRecordEntity sendRichtextMail(String sender, String[] to, String templateCode, Map<String, Object> variables, LocaleEnum locale) {
        RichtextEntity richtext = richtextService.getOne(Wrappers.<RichtextEntity>lambdaQuery().eq(RichtextEntity::getType, RichtextTypeEnum.EMAIL_TEMPLATE.name()).eq(RichtextEntity::getCode, templateCode).eq(RichtextEntity::getLocale, locale.name()));
        ApiAssert.notNull(richtext, RichtextCode.Message.NOT_EXISTS, templateCode);
        return this.sendHtmlMail(sender, to, richtext.getSubject(), richtext.getContent(), variables, templateCode, locale);
    }

    @Override
    public EmailRecordEntity sendHtmlMail(String sender, String[] to, String subject, String content, Map<String, Object> variables) {
        return this.sendHtmlMail(sender, to, subject, content, variables, null, LocaleUtils.locale());
    }

    @Override
    public EmailRecordEntity sendHtmlMail(String sender, String[] to, String subject, String content, Map<String, Object> variables, String templateCode, LocaleEnum locale) {
        EmailSenderHolder.MailSender mailSender = emailSenderHolder.get(sender);
        subject = TextReplaceUtils.replace(subject, variables);
        String text = TextReplaceUtils.replace(content, variables);

        EmailRecordEntity emailRecord = EmailRecordEntity.builder()
                .templateCode(templateCode)
                .locale(locale)
                .sendTo(String.join(SymbolConstants.COMMA, Lists.newArrayList(to)))
                .subject(subject)
                .content(text)
                .channel(mailProperties.getHost())
                .build();

        try {
            //true表示需要创建一个multipart message
            if (emailConfig.getRealSwitchFlag()) {
                MimeMessageHelper helper = new MimeMessageHelper(mailSender.getMimeMessage(), true, "utf-8");
                helper.setTo(to);
                if (Objects.nonNull(mailSender.getReplyTo())) {
                    helper.setReplyTo(mailSender.getReplyTo());
                }
                helper.setSubject(subject);
                helper.setText(text, true);
                emailSenderHolder.get(sender).send();
            }
            emailRecord.setSuccessFlag(true);
        } catch (Exception e) {
            log.error("发送html邮件时发生异常！", e);
            emailRecord.setSuccessFlag(false);
            emailRecord.setRemark(e.getMessage());
        } finally {
            emailRecordService.save(emailRecord);
        }
        return emailRecord;
    }

    @Override
    public EmailRecordEntity sendSimpleMail(String[] to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
        return new EmailRecordEntity();
    }

    @Override
    public EmailRecordEntity sendTemplateMail(String[] to, String subject, String templateName, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        String emailContent = templateEngine.process(templateName, context);
        return this.sendHtmlMail(null, to, subject, emailContent, null);
    }

    @Override
    public EmailRecordEntity sendAttachmentsMail(String[] to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            message.setFrom(mailProperties.getUsername());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);

            mailSender.send(message);
            log.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送带附件的邮件时发生异常！", e);
        }
        return new EmailRecordEntity();
    }

    @Override
    public EmailRecordEntity sendInlineResourceMail(String[] to, String subject, String content, String rscPath, String rscId) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            message.setFrom(mailProperties.getUsername());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);

            mailSender.send(message);
            log.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
        return new EmailRecordEntity();
    }

}