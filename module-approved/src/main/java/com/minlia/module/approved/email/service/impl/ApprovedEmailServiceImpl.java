package com.minlia.module.approved.email.service.impl;

import com.google.common.collect.Lists;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.approved.constant.RichtextCode;
import com.minlia.module.approved.email.entity.EmailRecord;
import com.minlia.module.approved.email.entity.Richtext;
import com.minlia.module.approved.email.enumeration.LocaleEnum;
import com.minlia.module.approved.email.service.ApprovedEmailRecordService;
import com.minlia.module.approved.email.service.ApprovedEmailService;
import com.minlia.module.approved.email.util.TextReplaceUtils;
import com.minlia.module.common.constant.SymbolConstants;
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
public class ApprovedEmailServiceImpl implements ApprovedEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private ApprovedEmailRecordService approvedEmailRecordService;

    @Override
    public EmailRecord sendRichtextMail(String recipient, String[] to, String templateCode, Map<String, ?> variables) {
        return this.sendRichtextMail(recipient, to, templateCode, variables, LocaleEnum.valueOf(LocaleContextHolder.getLocale().toString()));
    }

    @Override
    public EmailRecord sendRichtextMail(String recipient, String[] to, String templateCode, Map<String, ?> variables, LocaleEnum locale) {
        Richtext richtext = approvedEmailRecordService.queryRichtextByTypeAndCode("EMAIL_TEMPLATE", templateCode, locale);
        ApiAssert.notNull(richtext, RichtextCode.Message.NOT_EXISTS, templateCode);
        return this.sendHtmlMail(recipient, to, richtext.getSubject(), richtext.getContent(), variables, templateCode, locale);
    }

    @Override
    public EmailRecord sendHtmlMail(String recipient, String[] to, String subject, String content, Map<String, ?> variables, String templateCode, LocaleEnum locale) {

        MimeMessage message = mailSender.createMimeMessage();
        subject = TextReplaceUtils.replace(subject, variables);
        String text = TextReplaceUtils.replace(content, variables);

        EmailRecord emailRecord = EmailRecord.builder()
                .number(null)
                .templateCode(templateCode)
                .recipient(recipient)
                .sendTo(String.join(SymbolConstants.COMMA, Lists.newArrayList(to)))
                .subject(subject)
                .content(text)
                .locale(locale)
                .channel(mailProperties.getHost())
                .build();

        try {
            Boolean realSwitchFlag = approvedEmailRecordService.queryRealSwitchFlag();
            //true表示需要创建一个multipart message
            if (realSwitchFlag) {
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
            approvedEmailRecordService.insertSelective(emailRecord);
        }
        return emailRecord;
    }

}