package com.minlia.module.email.bean.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTO {

    /**
     * 目标地址
     */
    private String[] to;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件正文
     */
    private String content;

//    /**
//     * 模版名称
//     */
//    private String templateName;
//
//    /**
//     * 模版参数:json
//     */
//    private String templateParameter;

}
