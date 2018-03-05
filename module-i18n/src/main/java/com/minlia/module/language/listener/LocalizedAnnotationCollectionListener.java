//package com.minlia.module.language.v1.listener;
//
//import com.minlia.cloud.annotation.i18n.Localize;
//import com.minlia.cloud.annotation.i18n.Localized;
//import java.lang.reflect.Method;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ReflectionUtils;
//
//@Slf4j
//@Component
//public class LocalizedAnnotationCollectionListener implements BeanPostProcessor {
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        log.debug("开始初始化国际化");
//        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
//        if (methods != null) {
//            for (Method method : methods) {
//                Localized localized = AnnotationUtils.findAnnotation(method, Localized.class);
//                if (null != localized) {
//                    int length = localized.values().length;
//                    if (length > 0) {
//                        for (Localize localize : localized.values()) {
//                            createIfNotFound(localize);
//                        }
//                    }
//                }
//                Localize localize = AnnotationUtils.findAnnotation(method, Localize.class);
//                if (null != localize) {
//                    if (null != localize) {
//                        createIfNotFound(localize);
//                    }
//                }
//
//            }
//        }
//        return bean;
//    }
//
//    private void createIfNotFound(Localize localize) {
//        log.debug("Locale: {} with message: {}", localize.locale(), localize.message());
//    }
//
//}
