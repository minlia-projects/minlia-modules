package com.minlia.module.sms.service.impl;

import com.google.common.collect.Lists;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.common.constant.SymbolConstants;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import com.minlia.module.richtext.constant.RichtextCode;
import com.minlia.module.richtext.entity.Richtext;
import com.minlia.module.richtext.enumeration.RichtextTypeEnum;
import com.minlia.module.richtext.service.RichtextService;
import com.minlia.module.sms.config.SmsConfig;
import com.minlia.module.sms.constant.SmsCode;
import com.minlia.module.sms.entity.SmsRecord;
import com.minlia.module.sms.service.SmsRecordService;
import com.minlia.module.sms.service.SmsService;
import com.minlia.module.sms.util.TextReplaceUtils;
import com.minlia.modules.aliyun.sms.SmsSendService;
import com.minlia.modules.otp.sms.OtpSmsService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
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
    private OtpSmsService otpSmsService;

    @Autowired
    private RichtextService richtextService;

    @Autowired
    private SmsRecordService smsRecordService;

    @Autowired
    private SmsSendService aliyunSmsSendService;

    @Override
    public SmsRecord sendRichtextSms(String[] to, String templateCode, Map<String, ?> variables) {
        return this.sendRichtextSms(null, to, templateCode, variables, LocaleEnum.valueOf(LocaleContextHolder.getLocale().toString()));
    }

    @Override
    public SmsRecord sendRichtextSms(String[] to, String templateCode, Map<String, ?> variables, LocaleEnum locale) {
        return this.sendRichtextSms(null, to, templateCode, variables, LocaleEnum.valueOf(LocaleContextHolder.getLocale().toString()));
    }

    @Override
    public SmsRecord sendRichtextSms(String recipient, String[] to, String templateCode, Map<String, ?> variables) {
        return this.sendRichtextSms(recipient, to, templateCode, variables, LocaleEnum.valueOf(LocaleContextHolder.getLocale().toString()));
    }

    @Override
    public SmsRecord sendRichtextSms(String recipient, String[] to, String templateCode, Map<String, ?> variables, LocaleEnum locale) {
        Richtext richtext = richtextService.queryByTypeAndCode(RichtextTypeEnum.SMS_TEMPLATE.name(), templateCode, locale);
        ApiAssert.notNull(richtext, RichtextCode.Message.NOT_EXISTS, templateCode);
        String sendTo = String.join(SymbolConstants.COMMA, Lists.newArrayList(to));
        String content = TextReplaceUtils.replace(richtext.getContent(), variables);
        SmsRecord smsRecord = SmsRecord.builder().channel(smsConfig.getChannel()).recipient(recipient).sendTo(sendTo).code(templateCode).subject(richtext.getSubject()).content(content).locale(richtext.getLocale()).build();
        try {
            if (smsConfig.getRealSwitchFlag()) {
                switch (smsConfig.getChannel()) {
                    case ALIYUN:
                        boolean bool = aliyunSmsSendService.send(to[0], richtext.getThirdPartyCode(), JSONObject.fromObject(variables).toString());
                        smsRecord.setSuccessFlag(bool);
                        break;
                    case OPT:
                        String result = otpSmsService.send(null, sendTo, content);
                        smsRecord.setRemark(result);
                        smsRecord.setSuccessFlag(true);
                        break;
                    default:
                        ApiAssert.state(false, SmsCode.Message.UNSUPPORTED_WAY);
                }
            } else {
                smsRecord.setRemark("unreal");
            }
        } catch (Exception e) {
            smsRecord.setRemark(e.getMessage());
            smsRecord.setSuccessFlag(false);
        }
        smsRecordService.insertSelective(smsRecord);
        return smsRecord;
    }

}