package com.minlia.module.email.service;

import com.minlia.module.email.entity.EmailRecord;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * Created by garen on 2018/8/10.
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
    EmailRecord sendRichtextMail(String[] to, String templateCode, Map<String, Object> variables);

    /**
     * 发送文本邮件
     *
     * @param to
     * @param templateCode
     * @param variables
     * @return
     */
    @Async
    EmailRecord sendRichtextMail(String[] to, String templateCode, Map<String, Object> variables, LocaleEnum locale);

    /**
     * 发送文本邮件
     *
     * @param to
     * @param subject
     * @param content
     * @return
     */
    @Async
    EmailRecord sendSimpleMail(String[] to, String subject, String content);

    /**
     * 发送HTML邮件
     *
     * @param to
     * @param subject
     * @param content
     * @return
     */
    @Async
    EmailRecord sendHtmlMail(String[] to, String subject, String content, Map<String, Object> variables);

    @Async
    EmailRecord sendHtmlMail(String[] to, String subject, String content, Map<String, Object> variables, String templateCode, LocaleEnum locale);

    /**
     * 发送模版邮件
     *
     * @param to
     * @param subject
     * @param templateName
     * @param variables
     * @return
     */
    EmailRecord sendTemplateMail(String[] to, String subject, String templateName, Map<String, Object> variables);

    /**
     * 发送带附件邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param filePath
     * @return
     */
    EmailRecord sendAttachmentsMail(String[] to, String subject, String content, String filePath);

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
    EmailRecord sendInlineResourceMail(String[] to, String subject, String content, String rscPath, String rscId);

}
