package com.minlia.module.email.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.email.entity.EmailRecord;
import com.minlia.module.email.mapper.EmailMapper;
import com.minlia.module.email.service.EmailService;
import com.minlia.module.email.util.TextReplaceUtils;
import com.minlia.module.richtext.constant.RichtextCode;
import com.minlia.module.richtext.entity.Richtext;
import com.minlia.module.richtext.enumeration.RichtextTypeEnum;
import com.minlia.module.richtext.service.RichtextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by garen on 2018/8/10.
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailMapper emailMapper;

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

    @Override
    public EmailRecord sendRichtextMail(String[] to, String richtextCode, Map<String, ?> variables) {
        Richtext richtext = richtextService.queryByTypeAndCode(RichtextTypeEnum.EMAIL_TEMPLATE.name(), richtextCode);
        ApiAssert.notNull(richtext, RichtextCode.Message.NOT_EXISTS, richtextCode);
        return this.sendHtmlMail(to, richtext.getSubject(), richtext.getContent(), variables);
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
    public EmailRecord sendHtmlMail(String[] to, String subject, String content, Map<String, ?> variables) {
        if (null == content) {
            content = "<html>\n" +
                    "<ro>\n" +
                    "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                    "</ro>\n" +
                    "</html>";
        }

        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            message.setFrom(mailProperties.getUsername());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(TextReplaceUtils.replace(content, variables), true);
            mailSender.send(message);
            log.info("html邮件发送成功");
        } catch (MessagingException e) {
            log.error("发送html邮件时发生异常！", e);
        }
        return new EmailRecord();
    }

    @Override
    public EmailRecord sendTemplateMail(String[] to, String subject, String templateName, Map<String, ?> variables) {
        Context context = new Context();
        context.setVariables(variables);
        String emailContent = templateEngine.process(templateName, context);
        return sendHtmlMail(to, subject, emailContent, null);
    }

    @Override
    public EmailRecord sendAttachmentsMail(String[] to, String subject, String content, String filePath){
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
    public EmailRecord sendInlineResourceMail(String[] to, String subject, String content, String rscPath, String rscId){
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

    @Override
    public EmailRecord update(EmailRecord emailRecord) {
        return null;
    }

    @Override
    public void delete(String number) {
        emailMapper.delete(number);
    }

    @Override
    public EmailRecord one(EmailRecord emailRecord) {
        return emailMapper.one(emailRecord);
    }

    @Override
    public List<EmailRecord> list(EmailRecord emailRecord) {
        return emailMapper.list(emailRecord);
    }

    @Override
    public PageInfo<EmailRecord> page(EmailRecord emailRecord, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(),pageable.getPageSize()).doSelectPageInfo(()-> emailMapper.list(emailRecord));
    }

}