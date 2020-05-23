package com.minlia.module.email.ro;

import lombok.Data;

@Data
public class EmailBaseRO {

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
