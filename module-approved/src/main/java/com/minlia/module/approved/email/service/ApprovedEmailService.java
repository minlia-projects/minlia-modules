package com.minlia.module.approved.email.service;

import com.minlia.module.approved.email.entity.EmailRecord;
import com.minlia.module.approved.email.enumeration.LocaleEnum;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * Created by garen on 2018/8/10.
 */
public interface ApprovedEmailService {

    /**
     * 发送文本邮件
     *
     * @param to
     * @param templateCode
     * @param variables
     * @return
     */
    @Async
    EmailRecord sendRichtextMail(String recipient, String[] to, String templateCode, Map<String, ?> variables);

    /**
     * 发送文本邮件
     *
     * @param to
     * @param templateCode
     * @param variables
     * @return
     */
    @Async
    EmailRecord sendRichtextMail(String recipient, String[] to, String templateCode, Map<String, ?> variables, LocaleEnum locale);

    EmailRecord sendHtmlMail(String recipient, String[] to, String subject, String content, Map<String, ?> variables, String templateCode, LocaleEnum locale);

}
