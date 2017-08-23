package com.minlia.modules.rbac.listener;

import com.minlia.modules.rbac.context.PermissionContextHolder;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * Created by cqqianyi on 2017/8/14.
 */
@Component
@Slf4j
public class PermissionCollectorListener implements BeanPostProcessor {

  public static final String[] AUTHORITIES=new String []{"hasAnyAuthority", "hasAuthority"};

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
    if (methods != null) {
      for (Method method : methods) {
        PreAuthorize annotated = AnnotationUtils.findAnnotation(method, PreAuthorize.class);
        if (null != annotated) {
          if (annotated != null) {
            String annotatedValue = annotated.value();
            String value = grabPermission(annotatedValue, AUTHORITIES);
            if (!StringUtils.isEmpty(value)) {
              PermissionContextHolder.add(value);
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
  private String grabPermission(String source, String... types) {
    if (StringUtils.isEmpty(source)) {
      return null;
    }
    for (String type : types) {
      if (source.startsWith(type)) {
        String ret = source.replaceAll(type + "\\('(.*)'\\)", "$1").trim();
        if (!StringUtils.isEmpty(ret)) {
          return ret;
        }
      }
    }
    return null;
  }


}

