package com.minlia.module.captcha.service.impl;

import com.google.common.collect.Maps;
import com.minlia.cloud.code.Code;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.cloud.utils.Environments;
import com.minlia.cloud.utils.LocalDateUtils;
import com.minlia.module.captcha.config.CaptchaConfig;
import com.minlia.module.captcha.constant.CaptchaCode;
import com.minlia.module.captcha.entity.Captcha;
import com.minlia.module.captcha.enumeration.CaptchaMethodEnum;
import com.minlia.module.captcha.mapper.CaptchaMapper;
import com.minlia.module.captcha.risk.event.RiskCaptchaEvent;
import com.minlia.module.captcha.ro.CaptchaCRO;
import com.minlia.module.captcha.ro.CaptchaQRO;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.module.drools.service.ReloadDroolsRulesService;
import com.minlia.module.email.service.EmailService;
import com.minlia.module.riskcontrol.service.*;
import com.minlia.module.sms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.minlia.module.captcha.constant.CaptchaCode.CAPTCHA_DEFAULT_TEMPLATE;

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
        return this.sendByCellphone(cellphone, CAPTCHA_DEFAULT_TEMPLATE);
    }

    @Override
    public Captcha sendByCellphone(String cellphone, String templateCode) {
        log.debug("Sending security code for cellphone: {}", cellphone);
        Captcha captcha = this.save(cellphone, CaptchaMethodEnum.CELLPHONE);

//        RiskCaptchaEvent riskCaptchaEvent = new RiskCaptchaEvent();
//        riskCaptchaEvent.setSceneValue(cellphone);
//        KieService.execute(riskCaptchaEvent);

        if (captchaConfig.getRealSwitchFlag()) {
            Map variables = Maps.newHashMap();
            variables.put("code", captcha.getCode());
            variables.put("effectiveSeconds", captchaConfig.getEffectiveSeconds());
            smsService.sendRichtextSms(new String[]{cellphone}, templateCode, variables);
        }
        return captcha;
    }

    @Override
    public Captcha sendByEmail(String email) {
        return this.sendByEmail(email, CAPTCHA_DEFAULT_TEMPLATE);
    }

    @Override
    public Captcha sendByEmail(String email, String templateCode) {
        return this.sendByEmail(email, templateCode, null);
    }

    @Override
    public Captcha sendByEmail(String email, String templateCode, Map<String, Object> variables) {
        log.debug("Sending security code for email: {}", email);
        Captcha captcha = this.save(email, CaptchaMethodEnum.EMAIL);

//        RiskCaptchaEvent riskCaptchaEvent = new RiskCaptchaEvent();
//        riskCaptchaEvent.setSceneValue(email);
//        KieService.execute(riskCaptchaEvent);

        if (captchaConfig.getRealSwitchFlag()) {
            if (null == variables) {
                variables = Maps.newHashMap();
            }
            variables.put("code", captcha.getCode());
            variables.put("effectiveSeconds", captchaConfig.getEffectiveSeconds());
            emailService.sendRichtextMail(new String[]{email}, templateCode, variables);
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
        String code = captchaConfig.getRandomCodeFlag() ? RandomStringUtils.randomNumeric(captchaConfig.getSize()) : LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        //发送时间
        LocalDateTime currentDate = LocalDateTime.now();
        //有效时间
        LocalDateTime effectiveDate = currentDate.plusSeconds(captchaConfig.getEffectiveSeconds());

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

    private void updateByResend(Captcha captcha, String code, LocalDateTime currentDate, LocalDateTime effectiveDate) {
        //校验 TODO 最大获取频率分钟：30分钟
//        ApiPreconditions.is(captcha.getLocked(), 1,"验证码已超出当日发送上线"); TODO

        ApiAssert.state(!captcha.getLocked() || captcha.getLockTime().isBefore(LocalDateTime.now()), CaptchaCode.Message.ALREADY_LOCKED);


        ApiAssert.state(currentDate.isAfter(captcha.getSendTime().plusSeconds(captchaConfig.getIntervalSeconds())), CaptchaCode.Message.ONCE_PER_MINUTE);
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
    public Code validityByCellphone(String cellphone, String code) {
        return validityByCellphone(cellphone, code, true);
    }

    @Override
    public Code validityByEmail(String email, String code) {
        return validityByEmail(email, code, true);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Code validityByCellphone(String cellphone, String code, boolean throwsException) {
        Captcha captcha = captchaMapper.queryOne(CaptchaQRO.builder().cellphone(cellphone).build());
        return validity(captcha, code, throwsException);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Code validityByEmail(String email, String code, boolean throwsException) {
        Captcha captcha = captchaMapper.queryOne(CaptchaQRO.builder().email(email).build());
        return validity(captcha, code, throwsException);
    }

    private Code validity(Captcha captcha, String code, boolean throwsException) {
        if (throwsException) {
            ApiAssert.notNull(captcha, CaptchaCode.Message.NOT_FOUND);
            ApiAssert.state(!captcha.getUsed(), CaptchaCode.Message.ALREADY_USED);
            ApiAssert.state(!captcha.getLocked(), CaptchaCode.Message.ALREADY_LOCKED);
            ApiAssert.state(LocalDateUtils.localDateTimeToTimestamp(captcha.getEffectiveTime()) > System.currentTimeMillis(), CaptchaCode.Message.CAPTCHA_EXPIRED);
        } else {
            if (null == captcha) {
                return CaptchaCode.Message.NOT_FOUND;
            }
            if (captcha.getUsed()) {
                return CaptchaCode.Message.ALREADY_USED;
            }
            if (captcha.getLocked()) {
                return CaptchaCode.Message.ALREADY_LOCKED;
            }
            if (LocalDateUtils.localDateTimeToTimestamp(captcha.getEffectiveTime()) < System.currentTimeMillis()) {
                return CaptchaCode.Message.CAPTCHA_EXPIRED;
            }
        }

//        ApiAssert.state(captcha.getFailureCount() != captchaConfig.getMaxValidationFailureTimes(), CaptchaCode.Message.CAPTCHA_REPETITIOUS_ERROR);
        if (code.equals(captcha.getCode())) {
            captcha.setUsed(true);
            captchaMapper.update(captcha);
        } else {
            //错误次数+1
            captcha.setFailureCount(captcha.getFailureCount() + 1);
            //超过最大错误次数
            if (captcha.getFailureCount() >= captchaConfig.getMaxValidationFailureTimes()) {
                captcha.setLocked(true);    //锁定
                captcha.setLockTime(LocalDateTime.now().plusMinutes(captchaConfig.getLockMinutes()));      //锁定时间
            }
            captchaMapper.update(captcha);
            if (throwsException) {
                ApiAssert.state(code.equals(captcha.getCode()), CaptchaCode.Message.CAPTCHA_ERROR);
            } else {
                return CaptchaCode.Message.CAPTCHA_ERROR;
            }
        }
        return CaptchaCode.Message.VERIFY_SUCCESS;
    }

}
