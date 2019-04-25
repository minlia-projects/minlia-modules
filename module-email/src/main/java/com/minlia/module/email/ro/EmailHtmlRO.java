package com.minlia.module.email.ro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailHtmlRO extends EmailBaseRO {

    /**
     * 邮件正文
     */
    private String content;

}
