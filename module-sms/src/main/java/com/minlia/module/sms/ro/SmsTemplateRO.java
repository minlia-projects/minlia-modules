package com.minlia.module.sms.ro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsTemplateRO extends SmsBaseRO {

    /**
     * 模版名称
     */
    private String templateName;

}
