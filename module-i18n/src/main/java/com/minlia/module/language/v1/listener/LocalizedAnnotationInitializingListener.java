package com.minlia.module.language.v1.listener;

import com.google.common.base.CaseFormat;
import com.minlia.cloud.annotation.i18n.Localize;
import com.minlia.cloud.annotation.i18n.Localized;
import com.minlia.cloud.constant.Constants;
import com.minlia.cloud.utils.EnvironmentUtils;
import com.minlia.module.language.v1.messagesource.MessageAcceptor;
import com.minlia.module.language.v1.messagesource.Messages;
import com.minlia.module.language.v1.messagesource.util.LocaleUtils;
import eu.infomas.annotation.AnnotationDetector;
import eu.infomas.annotation.AnnotationDetector.TypeReporter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class LocalizedAnnotationInitializingListener implements
    ApplicationListener<ApplicationReadyEvent> {

  public static final String DOT = ".";
  @Autowired
  MessageAcceptor messageAcceptor;

  private void resolve(String className, Field field, Localize localize, Messages messages) {
    try {
      field.setAccessible(true);
      Object value = field.get(null);
      if (null != value && !StringUtils.isEmpty(value)) {
        Locale locale = LocaleUtils.toLocale(localize.locale());
        messages.addMessage(locale, toKeyFormat(value.toString()), localize.message());
      }
    } catch (Exception e) {
    }
  }

  private String toKeyFormat(String code) {
    String prefix = Constants.EXCEPTIONS_APICODE_PREFIX;
    prefix = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, prefix);
    prefix = prefix.replaceAll("_", DOT);
    String ret = prefix + DOT + code;
    return ret;
  }

  /**
   * 获取到所有注解的类,初始化到数据库
   */
  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    log.debug("获取到所有注解的类,初始化到数据库 LocalizedAnnotationInitializingListener");

    if (EnvironmentUtils.isProduction()) {
      return;
    }
    Messages messages = new Messages();

    final TypeReporter fieldReporter = new TypeReporter() {
      @SuppressWarnings("unchecked")
      public Class<? extends Annotation>[] annotations() {
        return new Class[]{Localized.class};
      }

      public void reportTypeAnnotation(Class<? extends Annotation> annotation, String className) {
        try {
          Class clz = Class.forName(className);
          Field[] fields = clz.getDeclaredFields();
          for (Field field : fields) {
            if (field.isAnnotationPresent(Localized.class)) {
//              log.debug("field {}", field);
              Localized localized = field.getDeclaredAnnotation(Localized.class);
              if (localized != null) {
                for (Localize localize : localized.values()) {
                  resolve(className, field, localize, messages);
                }
              }
            }
            if (field.isAnnotationPresent(Localize.class)) {
              Localize localize = field.getDeclaredAnnotation(Localize.class);
              if (null != localize) {
                resolve(className, field, localize, messages);
              }
            }
          }

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };
    final AnnotationDetector cf = new AnnotationDetector(fieldReporter);
    try {
      cf.detect();
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (!EnvironmentUtils.isProduction()) {
      messageAcceptor.setMessages(Constants.EXCEPTIONS_APICODE_PREFIX, messages);
    }
  }
}