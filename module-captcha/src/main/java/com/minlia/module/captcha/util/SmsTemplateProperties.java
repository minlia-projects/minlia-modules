package com.minlia.module.captcha.util;

import com.google.common.base.CaseFormat;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by will on 6/20/17.
 * TODO: save ro registry
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
        //UPPER_UNDERSCOR Java和C++常量的命名规则，如“UPPER_UNDERSCORE”。//CaseFormat是一种实用工具类，以提供不同的ASCII字符格式之间的转换
        String formatted = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
        return propertyResolver.getProperty(formatted);
    }

}
