package com.minlia.modules.otp.sms;


/**
 * @author chen junhan 569551869@qq.com
 */
public interface OtpSmsService {

    /**
     * 发送短信
     *
     * @param icc      区号
     * @param destAddr 手机号
     * @param message  短信信息
     * @return 邮件发送id
     */
    String sendSms(String icc, String destAddr, String message);

}
