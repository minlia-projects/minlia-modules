package com.minlia.module.email.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author garen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailHtmlSendRo extends EmailSendRo {

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件正文
     */
    private String content;

}
