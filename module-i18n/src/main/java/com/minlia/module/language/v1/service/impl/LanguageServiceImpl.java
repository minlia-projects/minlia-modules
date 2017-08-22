package com.minlia.module.language.v1.service.impl;

import com.minlia.module.language.v1.service.LanguageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LanguageServiceImpl implements LanguageService {
//
//    @Autowired
//    LanguageDao secureCodeDao;
//
//
//    @Autowired
//    SmsTemplateProperties smsTemplateProperties;
//
//
//    /**
//     * 清除已过期的code
//     */
//    @Scheduled(fixedRateString = ((FIXED_RATE_STRING * 1000) + 1) + "")
//    @Override
//    public void cleanExpired() {
//        //不区分租户与用户
//        //当前时间减去3分钟前的
//        DateTime now = DateTime.now();
//        DateTime dateTime = now.minusMinutes(FIXED_RATE_STRING);
//        //FIXED 未使用的验证码, 在超过时间后也需要清除
////        List<Language> found = secureCodeDao.findByCreatedDateBeforeAndUsed(dateTime.toDate(), true);
//        List<Language> found = secureCodeDao.findByCreatedDateBefore(dateTime.toDate());
//        for (Language code : found) {
//            secureCodeDao.delete(code);
//        }
//    }
//
//    @Override
//    public Boolean validity(String consumer, String code, LanguageSceneEnum scene) {
//        log.debug("Validity for consumer: {} with code: {}", consumer, code);
//        List<Language> found = secureCodeDao.findByConsumerAndCodeAndTypeAndUsed(consumer, code, scene, false);
//        if (found != null && found.size() >= 1) {
//            Language secureCode = found.get(0);
//            if (secureCode != null && code.equalsIgnoreCase(secureCode.getCode())) {
//                return Boolean.TRUE;
//            } else {
//                return Boolean.FALSE;
//            }
//        }
//        return Boolean.FALSE;
//    }
//
//    /**
//     * TODO: 安全性问题, 一分钟只能发送一次验证码, 需要等到时间后才能发送第二次
//     *
//     * @param body
//     * @param scene
//     * @return
//     */
//    @Override
//    public Language send(LanguageSendRequestBody body, LanguageSceneEnum scene) {
//
//        //检查用户名是否为空
//        ApiPreconditions.checkNotNull(body.getUsername(), CaptchaApiCode.NOT_NULL);
//
//        //获取信息样板类型属性
//        String smsTemplateId = smsTemplateProperties.get(scene.name());
//
//        //检查获取了类型
////        ApiPreconditions.checkNotNull(smsTemplateId, CaptchaApiCode.NOT_NULL, "短信模板没找到, 请先配置", false);
//
//        log.debug("Sending security code for username: {}", body.getUsername());
//
//        //已存在时需要清除所有之前的记录
//        List<Language> secureCodeListFound = secureCodeDao.findByConsumer(body.getUsername());
//        secureCodeDao.deleteInBatch(secureCodeListFound);
//
//        //创新新的记录
//        Language secureCode = new Language();
//        secureCode.setCode(RandomStringUtils.randomNumeric(4));
//        secureCode.setConsumer(body.getUsername());
//        secureCode.setUsed(Boolean.FALSE);
//        secureCode.setScene(scene);
//        this.secureCodeDao.save(secureCode);
//
//        //当生产环境时发送验证码, 否则不需要
//        //TODO: SMS模板建好后需要修改此处的jsonArguments中的内容
//        if (EnvironmentUtils.isProduction()) {
//            ContextHolder.getContext().getBean(AliyunSmsSendService.class).send(body.getUsername(), smsTemplateId, "{\"name\":\"" + secureCode.getCode() + "\"}");
//        } else {
//            ContextHolder.getContext().getBean(ConsoleSimulationSmsSendService.class).send(body.getUsername(), smsTemplateId, "{\"name\":\"" + secureCode.getCode() + "\"}");
//        }
//        return secureCode;
//    }
//
//    @Override
//    public void clean(String consumer, String code, LanguageSceneEnum scene) {
//        List<Language> found = secureCodeDao.findByConsumerAndCodeAndType(consumer, code, scene);
//        if (found != null && found.size() > 0) {
//            secureCodeDao.deleteInBatch(found);
//        }
//    }


}
