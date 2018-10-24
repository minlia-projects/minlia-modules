package com.minlia.modules.rbac.listener;

import com.minlia.modules.rbac.service.PermissionService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * Created by cqqianyi on 2017/8/14.
 */
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class PermissionCollectorListener implements BeanPostProcessor {

    public static final String[] AUTHORITIES = new String[]{"hasAnyAuthority", "hasAuthority"};

    @Autowired
    private PermissionService permissionService;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        if (null != methods && methods.length > 0) {
            for (Method method:methods) {
                PreAuthorize preAuthorize = AnnotationUtils.findAnnotation(method, PreAuthorize.class);
                if (null != preAuthorize) {
                    String annotatedValue = preAuthorize.value();
                    String[] values = grabPermission(annotatedValue, AUTHORITIES);
                    if (null != values) {
                        for (int i = 0; i < values.length; i++) {
                            permissionService.create(values[i], null);
                        }
                    }
                }
            }
        }
        return bean;
    }

    /**
     * 获取到权限点
     * @param source
     * @param types
     * @return
     */
    private String[] grabPermission(String source, String... types) {
        for (String type : types) {
            if (source.startsWith(type)) {
                String ret = source.replaceAll(type + "\\('(.*)'\\)", "$1").trim();
                if (!StringUtils.isEmpty(ret)) {
                    return ret.split(",");
                }
            }
        }
        return null;
    }

}

