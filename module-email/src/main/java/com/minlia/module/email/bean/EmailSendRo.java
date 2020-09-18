package com.minlia.module.email.bean;

import lombok.Data;

@Data
public class EmailSendRo {

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

}
