package com.minlia.module.captcha.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.cloud.utils.LocalDateUtils;
import com.minlia.module.captcha.bean.CaptchaCro;
import com.minlia.module.captcha.config.CaptchaConfig;
import com.minlia.module.captcha.constant.CaptchaCode;
import com.minlia.module.captcha.entity.CaptchaEntity;
import com.minlia.module.captcha.enumeration.CaptchaTypeEnum;
import com.minlia.module.captcha.mapper.CaptchaMapper;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.module.email.service.EmailService;
import com.minlia.module.i18n.util.LocaleUtils;
import com.minlia.module.sms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.minlia.module.captcha.constant.CaptchaCode.CAPTCHA_DEFAULT_TEMPLATE;

/**
 * <p>
 * 验证码 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-08-25
 */
@Slf4j
@Service
public class CaptchaServiceImpl extends ServiceImpl<CaptchaMapper, CaptchaEntity> implements CaptchaService {

    @Autowired
    private SmsService smsService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CaptchaConfig captchaConfig;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CaptchaEntity send(CaptchaCro cro) {
        CaptchaEntity entity;
        if (cro.getType().equals(CaptchaTypeEnum.CELLPHONE)) {
            ApiAssert.hasLength(cro.getCellphone(), CaptchaCode.Message.CELLPHONE_NOT_NULL);
            entity = this.sendByCellphone(cro.getCellphone());
        } else {
            ApiAssert.hasLength(cro.getEmail(), CaptchaCode.Message.EMAIL_NOT_NULL);
            entity = this.sendByEmail(cro.getEmail());
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CaptchaEntity sendByCellphone(String cellphone) {
        return this.sendByCellphone(cellphone, CAPTCHA_DEFAULT_TEMPLATE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CaptchaEntity sendByCellphone(String cellphone, String templateCode) {
        return this.sendByCellphone(cellphone, templateCode, Maps.newHashMap());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CaptchaEntity sendByCellphone(String cellphone, String templateCode, Map<String, Object> variables) {
        log.info("Sending security code for cellphone: {}", cellphone);
        CaptchaEntity entity = this.save(cellphone, CaptchaTypeEnum.CELLPHONE);
        if (captchaConfig.getRealSwitchFlag()) {
            variables.put("code", entity.getVcode());
            variables.put("effectiveSeconds", captchaConfig.getEffectiveSeconds());
            smsService.sendRichtextSms(new String[]{cellphone}, templateCode, variables, LocaleUtils.locale());
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CaptchaEntity sendByEmail(String email) {
        return this.sendByEmail(email, CAPTCHA_DEFAULT_TEMPLATE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CaptchaEntity sendByEmail(String email, String templateCode) {
        return this.sendByEmail(email, templateCode, Maps.newHashMap());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CaptchaEntity sendByEmail(String email, String templateCode, Map<String, Object> variables) {
        log.info("Sending security code for email: {}", email);
        CaptchaEntity entity = this.save(email, CaptchaTypeEnum.EMAIL);
        if (captchaConfig.getRealSwitchFlag()) {
            variables.put("code", entity.getVcode());
            variables.put("effectiveSeconds", captchaConfig.getEffectiveSeconds());
            emailService.sendRichtextMail(new String[]{email}, templateCode, variables, LocaleUtils.locale());
        }
        return entity;
    }

    public CaptchaEntity save(String sendTo, CaptchaTypeEnum captchaType) {
        //查询并判断是否存在
        CaptchaEntity entity = this.getOne(Wrappers.<CaptchaEntity>lambdaQuery().eq(CaptchaEntity::getEmail, sendTo).or().eq(CaptchaEntity::getCellphone, sendTo));
        //发送时间
        LocalDateTime currentDate = LocalDateTime.now();
        if (null == entity) {
            entity = CaptchaEntity.builder().lockFlag(false).useFlag(false).failureCount(0).build();
            switch (captchaType) {
                case EMAIL:
                    entity.setEmail(sendTo);
                    break;
                case CELLPHONE:
                    entity.setCellphone(sendTo);
                    break;
            }
        } else {
            //校验该号码是否被锁定
            ApiAssert.state(!entity.getLockFlag() || entity.getLockTime().isBefore(LocalDateTime.now()), CaptchaCode.Message.ALREADY_LOCKED);
            //校验距离上次发送时间
            ApiAssert.state(LocalDateTime.now().isAfter(entity.getSendTime().plusSeconds(captchaConfig.getIntervalSeconds())), CaptchaCode.Message.ONCE_PER_MINUTE);
            entity.setUseFlag(false);
            entity.setLockFlag(false);
            entity.setFailureCount(0);
            entity.setLockTime(currentDate);
        }
        //生成CODE
        String code = captchaConfig.getRandomCodeFlag() ? RandomStringUtils.randomNumeric(captchaConfig.getSize()) : LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        //有效时间
        LocalDateTime effectiveDate = currentDate.plusSeconds(captchaConfig.getEffectiveSeconds());
        entity.setVcode(code);
        entity.setSendTime(currentDate);
        entity.setEffectiveTime(effectiveDate);
        entity.setCountdown(captchaConfig.getEffectiveSeconds());
        this.saveOrUpdate(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response validity(String sendTo, String code) {
        CaptchaEntity entity = this.getOne(Wrappers.<CaptchaEntity>lambdaQuery().eq(CaptchaEntity::getEmail, sendTo).or().eq(CaptchaEntity::getCellphone, sendTo));
        return validity(entity, code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response validity(CaptchaEntity entity, String code) {
        if (null == entity) {
            return Response.failure(CaptchaCode.Message.NOT_FOUND);
        } else if (entity.getUseFlag()) {
            return Response.failure(CaptchaCode.Message.ALREADY_USED);
        } else if (entity.getLockFlag()) {
            return Response.failure(CaptchaCode.Message.ALREADY_LOCKED);
        } else if (LocalDateUtils.localDateTimeToTimestamp(entity.getEffectiveTime()) < System.currentTimeMillis()) {
            return Response.failure(CaptchaCode.Message.CAPTCHA_EXPIRED);
        }

        if (code.equals(entity.getVcode())) {
            entity.setUseFlag(true);
            this.saveOrUpdate(entity);
            return Response.success(CaptchaCode.Message.VERIFY_SUCCESS);
        } else {
            //错误次数+1
            entity.setFailureCount(entity.getFailureCount() + 1);
            //超过最大错误次数
            if (entity.getFailureCount() >= captchaConfig.getMaxValidationFailureTimes()) {
                entity.setLockFlag(true);    //锁定
                entity.setLockTime(LocalDateTime.now().plusMinutes(captchaConfig.getLockMinutes()));      //锁定时间
            }
            this.saveOrUpdate(entity);
            return Response.failure(CaptchaCode.Message.CAPTCHA_ERROR);
        }
    }

}