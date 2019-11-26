package com.minlia.module.sms.entity;

import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.sms.enums.SmsChannelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsRecord extends AbstractEntity {

    /**
     * 应用ID
     */
    private String appid;

    /**
     * 通道
     */
    private SmsChannelEnum channel;

    /**
     * 编码
     */
    private String code;

    /**
     * 接收方
     */
    private String recipient;

    /**
     * 接收人
     */
    private String sendTo;

    /**
     * 语言
     */
    private String locale;

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

    /**
     * 禁用标识
     */
    private Boolean disFlag;

}