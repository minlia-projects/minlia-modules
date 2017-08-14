package com.minlia.modules.starter.permission.collector;

import com.minlia.modules.security.annotation.Secured;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * Created by qianyi on 2017/8/14.
 */
@Component
public class PermissionCollectorListener implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        if (methods != null) {
            for (Method method : methods) {
                Secured secureds = AnnotationUtils.findAnnotation(method, Secured.class);
                if (null != secureds) {
                    //插入到数据中
//                    System.out.println(secureds.code());
//                    System.out.println(secureds.description());
                }
            }
        }
        return bean;
    }
}

