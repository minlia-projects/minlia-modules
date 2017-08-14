package com.minlia.modules.starter.permission.collector;

import com.minlia.modules.starter.permission.collector.annotations.Permissions;
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
public class PermissionCollectorListener  implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        if (methods != null) {
            for (Method method : methods) {
                Permissions permissions = AnnotationUtils.findAnnotation(method, Permissions.class);
                if (null != permissions) {
                    //插入到数据中
                    System.out.println(permissions.code());
                    System.out.println(permissions.description());
                }
            }
        }
        return bean;
    }
}

