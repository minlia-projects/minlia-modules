package com.minlia.module.captcha.service.impl;

import com.google.common.collect.Maps;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.cloud.utils.Environments;
import com.minlia.module.captcha.constant.CaptchaCode;
import com.minlia.module.captcha.entity.Captcha;
import com.minlia.module.captcha.enumeration.CaptchaMethodEnum;
import com.minlia.module.captcha.enumeration.CaptchaType;
import com.minlia.module.captcha.mapper.CaptchaMapper;
import com.minlia.module.captcha.ro.CaptchaCRO;
import com.minlia.module.captcha.ro.CaptchaQRO;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.module.captcha.util.SmsTemplateProperties;
import com.minlia.module.email.entity.EmailRecord;
import com.minlia.module.email.service.EmailService;
import com.minlia.modules.aliyun.sms.AliyunSmsSendService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private CaptchaMapper captchaMapper;

    @Autowired
    private SmsTemplateProperties smsTemplateProperties;

    @Override
    public Captcha send(CaptchaCRO cro) {
        Captcha captcha;
        if (cro.getMethod().equals(CaptchaMethodEnum.CELLPHONE)) {
            ApiAssert.hasLength(cro.getCellphone(), CaptchaCode.Message.CELLPHONE_NOT_NULL);
            captcha = this.sendByCellphone(cro.getCellphone());
        } else {
            ApiAssert.hasLength(cro.getEmail(), CaptchaCode.Message.EMAIL_NOT_NULL);
            captcha = this.sendByEmail(cro.getEmail());
        }
        return captcha;
    }

    @Override
    public Captcha sendByCellphone(String cellphone) {
        //获取信息样板类型属性
        String smsTemplateId=smsTemplateProperties.get(CaptchaType.SECURITY_CODE.name());

        //检查获取了类型
        ApiAssert.notNull(smsTemplateId, CaptchaCode.Message.TEMPLATE_NOT_FOUND);

        log.debug("Sending security code for cellphone: {}", cellphone);

        //查询并判断是否存在
        Captcha captcha = captchaMapper.queryOne(CaptchaQRO.builder().cellphone(cellphone).build());
//        ApiPreconditions.is(captcha.getLocked(), 1,"验证码已超出当日发送上线"); TODO

        String code = RandomStringUtils.randomNumeric(4);
        Date currentDate = new Date();
        Date effectiveDate = DateUtils.addMinutes(currentDate,5);

        if (null == captcha) {
            captcha = Captcha.builder()
                    .cellphone(cellphone)
                    .code(code)
                    .effectiveTime(effectiveDate)
                    .sendTime(currentDate)
                    .locked(false)
                    .used(false)
                    .failureCount(0)
                    .build();
            captchaMapper.create(captcha);
        } else {
            updateByResend(captcha, code, currentDate, effectiveDate);
        }

        //当生产环境时发送验证码, 否则不需要
        //TODO: SMS模板建好后需要修改此处的jsonArguments中的内容
        if(!Environments.isDevelopment()){
            boolean bool = ContextHolder.getContext().getBean(AliyunSmsSendService.class).send(cellphone, smsTemplateId, "{\"code\":\"" + captcha.getCode() + "\"}");
            ApiAssert.state(bool,CaptchaCode.Message.SEND_FAILURE);
        }
        return captcha;
    }

    @Override
    public Captcha sendByEmail(String email) {
        //查询并判断是否存在
        Captcha captcha = captchaMapper.queryOne(CaptchaQRO.builder().email(email).build());
//        ApiPreconditions.is(captcha.getLocked(), 1,"验证码已超出当日发送上线"); TODO

        String code = RandomStringUtils.randomNumeric(4);
        Date currentDate = new Date();
        Date effectiveDate = DateUtils.addMinutes(currentDate,5);

        if (null == captcha) {
            captcha = Captcha.builder()
                    .email(email)
                    .code(code)
                    .effectiveTime(effectiveDate)
                    .sendTime(currentDate)
                    .locked(false)
                    .used(false)
                    .failureCount(0)
                    .build();
            captchaMapper.create(captcha);
        } else {
            updateByResend(captcha, code, currentDate, effectiveDate);
        }

        //当生产环境时发送验证码, 否则不需要
        if(!Environments.isDevelopment()){
            Map vars = Maps.newHashMap();
            vars.put("code",code);
            EmailRecord emailRecord = emailService.sendTemplateMail(new String[]{email}, "验证码", "captcha", vars);
            ApiAssert.state(true,CaptchaCode.Message.SEND_FAILURE);
        }
        return captcha;
    }

    private void updateByResend(Captcha captcha, String code, Date currentDate, Date effectiveDate) {
        ApiAssert.state(currentDate.after(DateUtils.addMinutes(captcha.getSendTime(),1)) , CaptchaCode.Message.ONCE_PER_MINUTE);
        captcha.setCode(code);
        captcha.setUsed(false);
        captcha.setLocked(Boolean.FALSE);
        captcha.setFailureCount(0);
        captcha.setEffectiveTime(effectiveDate);
        captcha.setSendTime(currentDate);
        captchaMapper.update(captcha);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void validityByCellphone(String cellphone, String code) {
        Captcha captcha = captchaMapper.queryOne(CaptchaQRO.builder().cellphone(cellphone).build());
        validity(captcha, code);
    }

    @Override
    public void validityByEmail(String email, String code) {
        Captcha captcha = captchaMapper.queryOne(CaptchaQRO.builder().email(email).build());
        validity(captcha, code);
    }

    private void validity(Captcha captcha, String code){
        ApiAssert.notNull(captcha, CaptchaCode.Message.NOT_FOUND);
        ApiAssert.state(captcha.getEffectiveTime().getTime() > System.currentTimeMillis(), CaptchaCode.Message.CAPTCHA_EXPIRED);
        ApiAssert.state(captcha.getFailureCount() != 3, CaptchaCode.Message.CAPTCHA_REPETITIOUS_ERROR);
        ApiAssert.state(!captcha.getUsed(), CaptchaCode.Message.ALREADY_USED);

        if (code.equals(captcha.getCode())) {
            captcha.setUsed(true);
            captchaMapper.update(captcha);
        } else {
            captcha.setFailureCount(captcha.getFailureCount()+ 1);
            captchaMapper.update(captcha);
            ApiAssert.state(code.equals(captcha.getCode()), CaptchaCode.Message.CAPTCHA_ERROR);
        }
    }

}
