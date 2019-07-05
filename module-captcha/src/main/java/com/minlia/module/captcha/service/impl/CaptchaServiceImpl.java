package com.minlia.module.captcha.service.impl;

import com.google.common.collect.Maps;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.cloud.utils.Environments;
import com.minlia.module.captcha.config.CaptchaConfig;
import com.minlia.module.captcha.constant.CaptchaCode;
import com.minlia.module.captcha.entity.Captcha;
import com.minlia.module.captcha.enumeration.CaptchaMethodEnum;
import com.minlia.module.captcha.mapper.CaptchaMapper;
import com.minlia.module.captcha.ro.CaptchaCRO;
import com.minlia.module.captcha.ro.CaptchaQRO;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.module.email.service.EmailService;
import com.minlia.module.sms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private SmsService smsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CaptchaMapper captchaMapper;

    @Autowired
    private CaptchaConfig captchaConfig;

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
        log.debug("Sending security code for cellphone: {}", cellphone);
        Captcha captcha = this.save(cellphone, CaptchaMethodEnum.CELLPHONE);

        //当生产环境时发送验证码, 否则不需要
        if(!Environments.isDevelopment()){
            Map variables = Maps.newHashMap();
            variables.put("code", captcha.getCode());
            smsService.sendRichtextSms(new String[]{cellphone}, "CAPTCHA_DEFAULT", variables);
        }
        return captcha;
    }

    @Override
    public Captcha sendByEmail(String email) {
        log.debug("Sending security code for email: {}", email);
        Captcha captcha = this.save(email, CaptchaMethodEnum.EMAIL);

        //当生产环境时发送验证码, 否则不需要
        if(!Environments.isDevelopment()){
            Map variables = Maps.newHashMap();
            variables.put("code",captcha.getCode());
            emailService.sendTemplateMail(new String[]{email}, "验证码", "captcha", variables);
        }
        return captcha;
    }

    public Captcha save(String sendTo, CaptchaMethodEnum captchaMethod) {
        //查询并判断是否存在
        Captcha captcha = null;
        switch (captchaMethod) {
            case EMAIL:
                captcha = captchaMapper.queryOne(CaptchaQRO.builder().email(sendTo).build());
                break;
            case CELLPHONE:
                captcha = captchaMapper.queryOne(CaptchaQRO.builder().cellphone(sendTo).build());
                break;
        }

        //验证码CODE
        String code = Environments.isDevelopment() ? LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) : RandomStringUtils.randomNumeric(captchaConfig.getSize());
        //发送时间
        Date currentDate = new Date();
        //有效时间
        Date effectiveDate = DateUtils.addSeconds(currentDate, captchaConfig.getEffectiveSeconds());

        if (null == captcha) {
            captcha = Captcha.builder()
                    .code(code)
                    .sendTime(currentDate)
                    .effectiveTime(effectiveDate)
                    .locked(false)
                    .used(false)
                    .failureCount(0)
                    .build();
            switch (captchaMethod) {
                case EMAIL:
                    captcha.setEmail(sendTo);
                    break;
                case CELLPHONE:
                    captcha.setCellphone(sendTo);
                    break;
            }
            captchaMapper.create(captcha);
        } else {
            updateByResend(captcha, code, currentDate, effectiveDate);
        }
        captcha.setCountdown(captchaConfig.getEffectiveSeconds());
        return captcha;
    }

    private void updateByResend(Captcha captcha, String code, Date currentDate, Date effectiveDate) {
        //校验 TODO 最大获取频率分钟：30分钟
//        ApiPreconditions.is(captcha.getLocked(), 1,"验证码已超出当日发送上线"); TODO

        ApiAssert.state(!captcha.getLocked() || captcha.getLockTime().before(new Date()) , CaptchaCode.Message.ALREADY_LOCKED);


        ApiAssert.state(currentDate.after(DateUtils.addSeconds(captcha.getSendTime(), captchaConfig.getIntervalSeconds())) , CaptchaCode.Message.ONCE_PER_MINUTE);
        captcha.setCode(code);
        captcha.setUsed(false);
        captcha.setLocked(Boolean.FALSE);
        captcha.setFailureCount(0);
        captcha.setSendTime(currentDate);
        captcha.setEffectiveTime(effectiveDate);
        captcha.setLockTime(currentDate);
        captchaMapper.update(captcha);
    }

    @Override
    public Captcha queryOne(CaptchaQRO qro) {
        return captchaMapper.queryOne(qro);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void validityByCellphone(String cellphone, String code) {
        Captcha captcha = captchaMapper.queryOne(CaptchaQRO.builder().cellphone(cellphone).build());
        validity(captcha, code);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void validityByEmail(String email, String code) {
        Captcha captcha = captchaMapper.queryOne(CaptchaQRO.builder().email(email).build());
        validity(captcha, code);
    }

    private void validity(Captcha captcha, String code){
        ApiAssert.notNull(captcha, CaptchaCode.Message.NOT_FOUND);
        ApiAssert.state(!captcha.getUsed(), CaptchaCode.Message.ALREADY_USED);
        ApiAssert.state(!captcha.getLocked(), CaptchaCode.Message.ALREADY_LOCKED);
        ApiAssert.state(captcha.getEffectiveTime().getTime() > System.currentTimeMillis(), CaptchaCode.Message.CAPTCHA_EXPIRED);

//        ApiAssert.state(captcha.getFailureCount() != captchaConfig.getMaxValidationFailureTimes(), CaptchaCode.Message.CAPTCHA_REPETITIOUS_ERROR);
        if (code.equals(captcha.getCode())) {
            captcha.setUsed(true);
            captchaMapper.update(captcha);
        } else {
            //错误次数+1
            captcha.setFailureCount(captcha.getFailureCount()+ 1);
            //超过最大错误次数
            if (captcha.getFailureCount() > captchaConfig.getMaxValidationFailureTimes()) {
                captcha.setLocked(true);    //锁定
                captcha.setLockTime(DateUtils.addMinutes(new Date(), captchaConfig.getLockMinutes()));      //锁定时间
            }
            captchaMapper.update(captcha);
            ApiAssert.state(code.equals(captcha.getCode()), CaptchaCode.Message.CAPTCHA_ERROR);
        }
    }

}
