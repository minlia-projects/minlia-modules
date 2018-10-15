package com.minlia.module.captcha.service;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.cloud.utils.Environments;
import com.minlia.module.captcha.constant.CaptchaCode;
import com.minlia.module.captcha.bean.domain.Captcha;
import com.minlia.module.captcha.enumeration.CaptchaType;
import com.minlia.module.captcha.mapper.CaptchaMapper;
import com.minlia.module.captcha.util.SmsTemplateProperties;
import com.minlia.modules.aliyun.sms.AliyunSmsSendService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private CaptchaMapper captchaMapper;

    @Autowired
    private SmsTemplateProperties smsTemplateProperties;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void validity(String cellphone, String code) {
        Captcha captcha = captchaMapper.queryByCellphone(cellphone);
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

    @Override
    public Captcha send(String cellphone) {
        //获取信息样板类型属性
        String smsTemplateId=smsTemplateProperties.get(CaptchaType.SECURITY_CODE.name());

        //检查获取了类型
        ApiAssert.notNull(smsTemplateId, CaptchaCode.Message.TEMPLATE_NOT_FOUND);

        log.debug("Sending security code for cellphone: {}", cellphone);

        //查询并判断是否存在
        Captcha captcha = captchaMapper.queryByCellphone(cellphone);
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
            ApiAssert.state(currentDate.after(DateUtils.addMinutes(captcha.getSendTime(),1)) , CaptchaCode.Message.ONCE_PER_MINUTE);
            captcha.setCode(code);
            captcha.setUsed(false);
            captcha.setLocked(Boolean.FALSE);
            captcha.setFailureCount(0);
            captcha.setEffectiveTime(effectiveDate);
            captcha.setSendTime(currentDate);
            captchaMapper.update(captcha);
        }

        //当生产环境时发送验证码, 否则不需要
        //TODO: SMS模板建好后需要修改此处的jsonArguments中的内容
        if(!Environments.isDevelopment()){
            boolean bool = ContextHolder.getContext().getBean(AliyunSmsSendService.class).send(cellphone, smsTemplateId, "{\"code\":\"" + captcha.getCode() + "\"}");
            log.info("短信模板消息：————————————————————————————————————————————smsTemplateId:"+smsTemplateId);
            ApiAssert.state(bool,CaptchaCode.Message.SEND_FAILURE);
        }
        return captcha;
    }

}
