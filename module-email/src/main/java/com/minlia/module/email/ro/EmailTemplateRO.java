package com.minlia.module.email.ro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplateRO extends EmailBaseRO {

    /**
     * 模版名称
     */
    private String templateName;

    /**
     * 邮件主题
     */
    private String subject;

}
