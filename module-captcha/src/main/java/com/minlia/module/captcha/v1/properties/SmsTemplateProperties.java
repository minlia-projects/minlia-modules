package com.minlia.module.captcha.v1.properties;

import com.google.common.base.CaseFormat;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by will on 6/20/17.
 * TODO: save to registry
 */
@Component
public class SmsTemplateProperties implements EnvironmentAware {

    /**
     * 短信模板配置前缀
     */
    public static final String SMS_TEMPLATES_PREFIX = "sms.templates.";

    /**
     * 相对路径Property解析器
     */
    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, SMS_TEMPLATES_PREFIX);
    }

    /**
     * 根据枚举取得值
     */
    public String get(String key) {
        String formatted = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
        return propertyResolver.getProperty(formatted);
    }


}
