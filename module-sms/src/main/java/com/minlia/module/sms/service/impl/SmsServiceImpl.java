package com.minlia.module.sms.service.impl;

import com.google.common.collect.Lists;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.richtext.constant.RichtextCode;
import com.minlia.module.richtext.entity.Richtext;
import com.minlia.module.richtext.enumeration.RichtextTypeEnum;
import com.minlia.module.richtext.service.RichtextService;
import com.minlia.module.sms.entity.SmsRecord;
import com.minlia.module.sms.property.SmsProperties;
import com.minlia.module.sms.service.SmsRecordService;
import com.minlia.module.sms.service.SmsService;
import com.minlia.module.sms.util.TextReplaceUtils;
import com.minlia.modules.otp.sms.OtpSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by garen on 2018/8/10.
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SmsProperties smsProperties;

    @Autowired
    private RichtextService richtextService;

    @Autowired
    private SmsRecordService smsRecordService;

    @Autowired
    private OtpSmsService otpSmsService;

    @Override
    public SmsRecord sendRichtextSms(String[] to, String richtextCode, Map<String, ?> variables) {
        Richtext richtext = richtextService.queryByTypeAndCode(RichtextTypeEnum.SMS_TEMPLATE.name(), richtextCode);
        ApiAssert.notNull(richtext, RichtextCode.Message.NOT_EXISTS, richtextCode);
        String content = TextReplaceUtils.replace(richtext.getContent(), variables);
        String result = otpSmsService.send(null, String.join(",", Lists.newArrayList(to)), content);
        SmsRecord smsRecord = SmsRecord.builder().channel(smsProperties.getType()).to(String.join(",", Lists.newArrayList(to))).code(richtextCode).subject(richtext.getSubject()).content(content).locale(richtext.getLocale()).remark(result).build();
        smsRecordService.insertSelective(smsRecord);
        return smsRecord;
    }

}