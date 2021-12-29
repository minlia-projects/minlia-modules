package com.minlia.module.sms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.minlia.cloud.constant.SymbolConstants;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.i18n.enums.LocaleEnum;
import com.minlia.module.richtext.constant.RichtextCode;
import com.minlia.module.richtext.entity.RichtextEntity;
import com.minlia.module.richtext.enumeration.RichtextTypeEnum;
import com.minlia.module.richtext.service.RichtextService;
import com.minlia.module.sms.config.SmsConfig;
import com.minlia.module.sms.entity.SmsRecordEntity;
import com.minlia.module.sms.property.SmsProperties;
import com.minlia.module.sms.service.SmsRecordService;
import com.minlia.module.sms.service.SmsService;
import com.minlia.module.sms.util.TextReplaceUtils;
import com.minlia.modules.aliyun.sms.AliyunSmsSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by garen on 2018/8/10.
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SmsConfig smsConfig;
    @Autowired
    private SmsProperties smsProperties;
    @Autowired
    private RichtextService richtextService;
    @Autowired
    private SmsRecordService smsRecordService;
    @Autowired
    private AliyunSmsSendService aliyunSmsSendService;

    private static final Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();

    @Override
    public SmsRecordEntity sendRichtextSms(String[] to, String richtextCode, Map<String, ?> variables) {
        return this.sendRichtextSms(to, richtextCode, variables, LocaleEnum.valueOf(LocaleContextHolder.getLocale().toString()));
    }

    @Override
    public SmsRecordEntity sendRichtextSms(String[] to, String richtextCode, Map<String, ?> variables, LocaleEnum locale) {
        RichtextEntity richtext = richtextService.getOne(Wrappers.<RichtextEntity>lambdaQuery().eq(RichtextEntity::getType, RichtextTypeEnum.SMS_TEMPLATE.name()).eq(RichtextEntity::getCode, richtextCode).eq(RichtextEntity::getLocale, locale));
        ApiAssert.notNull(richtext, RichtextCode.Message.NOT_EXISTS, richtextCode);
        String content = TextReplaceUtils.replace(richtext.getContent(), variables);
        SmsRecordEntity smsRecord = SmsRecordEntity.builder().channel(smsProperties.getType()).sendTo(String.join(SymbolConstants.COMMA, Lists.newArrayList(to))).templateCode(richtextCode).subject(richtext.getSubject()).content(content).locale(richtext.getLocale()).build();
        try {
            if (smsConfig.getRealSwitchFlag()) {
                Boolean result = aliyunSmsSendService.send(to[0], richtext.getSubject(), gson.toJson(variables));
                smsRecord.setRemark(result.toString());
//                String result = otpSmsService.send(null, String.join(SymbolConstants.COMMA, Lists.newArrayList(to)), content);
//                smsRecord.setRemark(result);
            } else {
                smsRecord.setRemark("unreal");
            }
            smsRecord.setSuccessFlag(true);
        } catch (Exception e) {
            smsRecord.setRemark(e.getMessage());
            smsRecord.setSuccessFlag(false);
        }
        smsRecordService.save(smsRecord);
        return smsRecord;
    }

}