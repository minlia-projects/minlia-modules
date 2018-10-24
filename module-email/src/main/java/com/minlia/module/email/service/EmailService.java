package com.minlia.module.email.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.email.bean.domain.EmailRecord;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by garen on 2018/8/10.
 */
public interface EmailService {

    /**
     * 发送文本邮件
     * @param to
     * @param subject
     * @param content
     * @return
     */
    EmailRecord sendSimpleMail(String[] to, String subject, String content);

    /**
     * 发送HTML邮件
     * @param to
     * @param subject
     * @param content
     * @return
     */
    EmailRecord sendHtmlMail(String[] to, String subject, String content);

    /**
     * 发送模版邮件
     * @param to
     * @param subject
     * @param templateName
     * @param variables
     * @return
     */
    EmailRecord sendTemplateMail(String[] to, String subject, String templateName, Map<String, ?> variables);

    /**
     * 发送带附件邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     * @return
     */
    EmailRecord sendAttachmentsMail(String[] to, String subject, String content, String filePath);

    /**
     * 发送静态文件邮件
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     * @return
     */
    EmailRecord sendInlineResourceMail(String[] to, String subject, String content, String rscPath, String rscId);

    EmailRecord update(EmailRecord emailRecord);

    void delete(String number);

    EmailRecord one(EmailRecord emailRecord);

    List<EmailRecord> list(EmailRecord emailRecord);

    PageInfo<EmailRecord> page(EmailRecord emailRecord, Pageable pageable);

}
