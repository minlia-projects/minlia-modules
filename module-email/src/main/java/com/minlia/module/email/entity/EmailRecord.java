package com.minlia.module.email.entity;

import com.minlia.module.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRecord extends AbstractEntity {

    /**
     * 应用ID
     */
    private String appid;

    /**
     * 编号
     */
    private String number;

    /**
     * 模版代码
     */
    private String templateCode;

    /**
     * 主送人
     */
    private String sendTo;

    /**
     * 抄送人
     */
    private String sendCc;

    /**
     * 秘送人
     */
    private String sendBcc;

    /**
     * 语言
     */
    private String locale;

    /**
     * 通道：阿里云、腾讯云、MINLIA
     */
    private String channel;

    /**
     * 主题
     */
    private String subject;

    /**
     * 内容
     */
    private String content;

    /**
     * 备注
     */
    private String remark;

    /**
     * 发送成功
     */
    private Boolean successFlag;

}