package com.minlia.module.captcha.v1.service.impl;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.cloud.utils.Environments;
import com.minlia.module.captcha.v1.body.SecureCodeSendRequestBody;
import com.minlia.module.captcha.v1.code.CaptchaApiCode;
import com.minlia.module.captcha.v1.domain.SecureCode;
import com.minlia.module.captcha.v1.enumeration.SecureCodeSceneEnum;
import com.minlia.module.captcha.v1.properties.SmsTemplateProperties;
import com.minlia.module.captcha.v1.repository.SecureCodeRepository;
import com.minlia.module.captcha.v1.service.SecureCodeService;
import com.minlia.modules.starter.sms.AliyunSmsSendService;
import com.minlia.modules.starter.sms.ConsoleSimulationSmsSendService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mybatis.support.GuidHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SecureCodeServiceImpl implements SecureCodeService {

  @Autowired
  SecureCodeRepository secureCodeRepository;


  @Autowired
  SmsTemplateProperties smsTemplateProperties;


  @Autowired
  GuidHolder guidHolder;
  /**
   * 清除已过期的code
   */
  @Scheduled(fixedRateString = ((FIXED_RATE_STRING * 1000) + 1) + "")
  @Override
  public void cleanExpired() {
    //不区分租户与用户
    //当前时间减去3分钟前的
//    DateTime now = DateTime.now();
    LocalDateTime zonedDateTime=LocalDateTime.now();
    LocalDateTime dateTime = zonedDateTime.minusMinutes(FIXED_RATE_STRING);
    //FIXED 未使用的验证码, 在超过时间后也需要清除
//        List<SecureCode> found = secureCodeRepository.findByCreatedDateBeforeAndUsed(dateTime.toDate(), true);
    List<SecureCode> found = secureCodeRepository.findByCreatedDateBefore(dateTime);
    for (SecureCode code : found) {
      secureCodeRepository.delete(code);
    }
  }

  @Override
  public Boolean validity(String consumer, String code, SecureCodeSceneEnum scene) {
    log.debug("Validity for consumer: {} with code: {}", consumer, code);
    List<SecureCode> found = secureCodeRepository
        .findByConsumerAndCodeAndSceneAndUsed(consumer, code, scene, false);
    if (found != null && found.size() >= 1) {
      SecureCode secureCode = found.get(0);
      if (secureCode != null && code.equalsIgnoreCase(secureCode.getCode())) {
        return Boolean.TRUE;
      } else {
        return Boolean.FALSE;
      }
    }
    return Boolean.FALSE;
  }

  /**
   * TODO: 安全性问题, 一分钟只能发送一次验证码, 需要等到时间后才能发送第二次
   */
  @Override
  public SecureCode send(SecureCodeSendRequestBody body, SecureCodeSceneEnum scene) {

    //检查用户名是否为空
    ApiPreconditions.checkNotNull(body.getUsername(), CaptchaApiCode.NOT_NULL);

    //获取信息样板类型属性
    String smsTemplateId = smsTemplateProperties.get(scene.name());

    //检查获取了类型
//        ApiPreconditions.checkNotNull(smsTemplateId, CaptchaApiCode.NOT_NULL, "短信模板没找到, 请先配置", false);

    log.debug("Sending secure code for username: {}", body.getUsername());

    //已存在时需要清除所有之前的记录
    List<SecureCode> secureCodeListFound = secureCodeRepository.findByConsumer(body.getUsername());
    secureCodeRepository.deleteInBatch(secureCodeListFound);

    //创新新的记录
    SecureCode secureCode = new SecureCode();
    secureCode.setCode(RandomStringUtils.randomNumeric(4));
    secureCode.setConsumer(body.getUsername());
    secureCode.setUsed(Boolean.FALSE);
    secureCode.setScene(scene);
    this.secureCodeRepository.save(secureCode);

    //当生产环境时发送验证码, 否则不需要
    //TODO: SMS模板建好后需要修改此处的jsonArguments中的内容
    if (Environments.isProduction()) {
      ContextHolder.getContext().getBean(AliyunSmsSendService.class)
          .send(body.getUsername(), smsTemplateId, "{\"name\":\"" + secureCode.getCode() + "\"}");
    } else {
      ContextHolder.getContext().getBean(ConsoleSimulationSmsSendService.class)
          .send(body.getUsername(), smsTemplateId, "{\"name\":\"" + secureCode.getCode() + "\"}");
    }
    return secureCode;
  }

  @Override
  public void clean(String consumer, String code, SecureCodeSceneEnum scene) {
    List<SecureCode> found = secureCodeRepository
        .findByConsumerAndCodeAndScene(consumer, code, scene);
    if (found != null && found.size() > 0) {
      secureCodeRepository.deleteInBatch(found);
    }
  }


}
