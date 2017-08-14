package com.minlia.modules.starter.permission.collector;

import com.minlia.modules.starter.permission.collector.annotations.Permissions;
import com.minlia.modules.starter.permission.collector.dao.PermissionDao;
import com.minlia.modules.starter.permission.collector.entity.Permission;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * Created by qianyi on 2017/8/14.
 * 权限Code和description扫描
 */
@Component
public class PermissionCollectorListener implements BeanPostProcessor {


    @Autowired
    private PermissionDao permissionDao;

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
                    Permission permissionfind = permissionDao.findByCode(permissions.code());
                    if (permissionfind != null) {
                        //如果存在则更新 权限Code描述
                        permissionfind.setDescription(permissions.description());
                        permissionDao.update(permissionfind);
                    } else {
                        //插入到数据中
                        permissionfind.setCode(permissions.code());
                        permissionfind.setDescription(permissions.description());
                        permissionDao.insert(permissionfind);
                    }
                }
            }
        }
        return bean;
    }
}

