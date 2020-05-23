package com.minlia.module.email.service.impl;

import com.google.common.collect.Lists;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.common.constant.SymbolConstants;
import com.minlia.module.email.config.EmailConfig;
import com.minlia.module.email.entity.EmailRecord;
import com.minlia.module.email.service.EmailRecordService;
import com.minlia.module.email.service.EmailService;
import com.minlia.module.email.util.TextReplaceUtils;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import com.minlia.module.richtext.constant.RichtextCode;
import com.minlia.module.richtext.entity.Richtext;
import com.minlia.module.richtext.enumeration.RichtextTypeEnum;
import com.minlia.module.richtext.service.RichtextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.i18n.LocaleContextHolder;
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

/**
 * Created by garen on 2018/8/10.
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    private JavaMailSender mailSender;

//    @Value("${spring.mail.username}")
//    private String sender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private RichtextService richtextService;

    @Autowired
    private EmailRecordService emailRecordService;

    @Override
    public EmailRecord sendRichtextMail(String[] to, String templateCode, Map<String, Object> variables) {
        Richtext richtext = richtextService.queryByTypeAndCode(RichtextTypeEnum.EMAIL_TEMPLATE.name(), templateCode);
        ApiAssert.notNull(richtext, RichtextCode.Message.NOT_EXISTS, templateCode);
        return this.sendHtmlMail(to, richtext.getSubject(), richtext.getContent(), variables);
    }

    @Override
    public EmailRecord sendRichtextMail(String[] to, String templateCode, Map<String, Object> variables, LocaleEnum locale) {
        Richtext richtext = richtextService.queryByTypeAndCode(RichtextTypeEnum.EMAIL_TEMPLATE.name(), templateCode, locale);
        ApiAssert.notNull(richtext, RichtextCode.Message.NOT_EXISTS, templateCode);
        return this.sendHtmlMail(to, richtext.getSubject(), richtext.getContent(), variables, templateCode, locale);
    }

    @Override
    public EmailRecord sendSimpleMail(String[] to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
        return new EmailRecord();
    }

    @Override
    public EmailRecord sendTemplateMail(String[] to, String subject, String templateName, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        String emailContent = templateEngine.process(templateName, context);
        return this.sendHtmlMail(to, subject, emailContent, null);
    }

    @Override
    public EmailRecord sendHtmlMail(String[] to, String subject, String content, Map<String, Object> variables) {
        return this.sendHtmlMail(to, subject, content, variables, null, LocaleEnum.valueOf(LocaleContextHolder.getLocale().toString()));
    }

    @Override
    public EmailRecord sendHtmlMail(String[] to, String subject, String content, Map<String, Object> variables, String templateCode, LocaleEnum locale) {
        if (null == content) {
            content = "<html>\n" +
                    "<ro>\n" +
                    "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                    "</ro>\n" +
                    "</html>";
        }

        MimeMessage message = mailSender.createMimeMessage();
        subject = TextReplaceUtils.replace(subject, variables);
        String text = TextReplaceUtils.replace(content, variables);

        EmailRecord emailRecord = EmailRecord.builder()
                .number(null)
                .templateCode(templateCode)
                .sendTo(String.join(SymbolConstants.COMMA, Lists.newArrayList(to)))
                .subject(subject)
                .content(text)
                .locale(locale)
                .channel(mailProperties.getHost())
                .build();

        try {
            //true表示需要创建一个multipart message
            if (emailConfig.getRealSwitchFlag()) {
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
                message.setFrom(mailProperties.getUsername());
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(text, true);
                mailSender.send(message);
            }
            emailRecord.setSuccessFlag(true);
        } catch (Exception e) {
            log.error("发送html邮件时发生异常！", e);
            emailRecord.setSuccessFlag(false);
            emailRecord.setRemark(e.getMessage());
        } finally {
            emailRecordService.insertSelective(emailRecord);
        }
        return emailRecord;
    }


    @Override
    public EmailRecord sendAttachmentsMail(String[] to, String subject, String content, String filePath) {
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
        return new EmailRecord();
    }

    @Override
    public EmailRecord sendInlineResourceMail(String[] to, String subject, String content, String rscPath, String rscId) {
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
        return new EmailRecord();
    }

}