package com.minlia.module.sms.service;

import com.minlia.module.i18n.enumeration.LocaleEnum;
import com.minlia.module.sms.entity.SmsRecord;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * Created by garen on 2018/8/10.
 */
public interface SmsService {

    /**
     * 发送短信
     * @param to
     * @param richtextCode
     * @param variables
     * @return
     */
    @Async
    SmsRecord sendRichtextSms(String[] to, String richtextCode, Map<String, ?> variables);

    /**
     * 发送短信
     * @param to
     * @param richtextCode
     * @param variables
     * @param locale
     * @return
     */
    @Async
    SmsRecord sendRichtextSms(String[] to, String richtextCode, Map<String, ?> variables, LocaleEnum locale);

}
