package com.minlia.module.sms.ro;

import lombok.Data;

@Data
public class SmsBaseRO {

    /**
     * 发送给
     */
    private String[] to;

    /**
     * 抄送给
     */
    private String[] cc;

    /**
     * 密送给
     */
    private String[] bcc;

    /**
     * 邮件主题
     */
    private String subject;

}
