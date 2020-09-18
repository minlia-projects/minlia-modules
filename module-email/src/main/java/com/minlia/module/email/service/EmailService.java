package com.minlia.module.email.service;

import com.minlia.module.email.entity.EmailRecordEntity;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * @author garen
 * @date 2018/8/10
 */
public interface EmailService {

    /**
     * 发送文本邮件
     *
     * @param to
     * @param templateCode
     * @param variables
     * @return
     */
    @Async
    EmailRecordEntity sendRichtextMail(String[] to, String templateCode, Map<String, Object> variables);

    /**
     * 发送文本邮件
     *
     * @param to
     * @param templateCode
     * @param variables
     * @return
     */
    @Async
    EmailRecordEntity sendRichtextMail(String[] to, String templateCode, Map<String, Object> variables, LocaleEnum locale);

    /**
     * 发送文本邮件
     *
     * @param to
     * @param subject
     * @param content
     * @return
     */
    @Async
    EmailRecordEntity sendSimpleMail(String[] to, String subject, String content);

    /**
     * 发送HTML邮件
     *
     * @param to
     * @param subject
     * @param content
     * @return
     */
    @Async
    EmailRecordEntity sendHtmlMail(String[] to, String subject, String content, Map<String, Object> variables);

    @Async
    EmailRecordEntity sendHtmlMail(String[] to, String subject, String content, Map<String, Object> variables, String templateCode, LocaleEnum locale);

    /**
     * 发送模版邮件
     *
     * @param to
     * @param subject
     * @param templateName
     * @param variables
     * @return
     */
    EmailRecordEntity sendTemplateMail(String[] to, String subject, String templateName, Map<String, Object> variables);

    /**
     * 发送带附件邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param filePath
     * @return
     */
    EmailRecordEntity sendAttachmentsMail(String[] to, String subject, String content, String filePath);

    /**
     * 发送静态文件邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     * @return
     */
    EmailRecordEntity sendInlineResourceMail(String[] to, String subject, String content, String rscPath, String rscId);

}
