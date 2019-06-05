package com.minlia.module.sms.ro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsHtmlRO extends SmsBaseRO {

    /**
     * 邮件正文
     */
    private String content;

}
