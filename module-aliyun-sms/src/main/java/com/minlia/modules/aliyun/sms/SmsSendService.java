package com.minlia.modules.aliyun.sms;

/**
 * 短信发送服务
 */
public interface SmsSendService {

    /**
     * 根据模板编号发送短信到手机号码
     * @param to
     * @param templateCode
     * @param jsonArgumentsObject
     * @return
     */
    boolean send(String to, String templateCode, String jsonArgumentsObject);

}