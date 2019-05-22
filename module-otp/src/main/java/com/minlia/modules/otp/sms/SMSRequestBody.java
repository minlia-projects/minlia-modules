package com.minlia.modules.otp.sms;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chen junhan 569551869@qq.com
 */
@Getter
@Setter
public class SMSRequestBody {
    /**
     * 区号
     */
    private String icc;
    /**
     * 手机号
     */
    private String destAddr;
    /**
     * 短信信息
     */
    private String message;
}
