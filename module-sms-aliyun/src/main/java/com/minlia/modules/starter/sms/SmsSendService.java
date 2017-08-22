package com.minlia.modules.starter.sms;

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
    public boolean send(String to, String templateCode, String jsonArgumentsObject);


    /**
     *
     * @param bibleCode
     * @param bibleItemCode
     * @param to
     * @param jsonArgumentsObject
     * @return
     */
//    public boolean send(String bibleCode,String bibleItemCode,String to,String jsonArgumentsObject);
}