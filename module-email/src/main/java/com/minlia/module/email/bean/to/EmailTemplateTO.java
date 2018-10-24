package com.minlia.module.email.bean.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplateTO {

    /**
     * 目标地址
     */
    private String toAddress;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 模版名称
     */
    private String templateName;

}
