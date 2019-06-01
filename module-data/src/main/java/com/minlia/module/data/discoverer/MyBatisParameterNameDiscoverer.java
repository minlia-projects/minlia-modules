package com.minlia.module.data.discoverer;

import org.apache.ibatis.annotations.Param;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * MyBatis接口参数名称发现器
 *
 * @author wangzhuhua
 * @date 2018/09/05 下午3:12
 **/
public class MyBatisParameterNameDiscoverer implements ParameterNameDiscoverer {

    @Override
    public String[] getParameterNames(Method method) {
        return getParameterNames(method.getParameters(), method.getParameterAnnotations());
    }

    @Override
    public String[] getParameterNames(Constructor<?> ctor) {
        return getParameterNames(ctor.getParameters(), ctor.getParameterAnnotations());
    }

    /**
     * Mybatis参数名称解析
     *
     * @param parameters           参数数组
     * @param parameterAnnotations 参数注解数组
     * @return 参数名称
     */
    private String[] getParameterNames(Parameter[] parameters, Annotation[][] parameterAnnotations) {
        String[] parameterNames = new String[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            String paramName = param.getName();

            // mybatis 自定义参数名称
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof Param) {
                    String customName = ((Param) annotation).value();
                    if (StringUtils.hasLength(customName)) {
                        paramName = customName;
                        break;
                    }
                }
            }

            parameterNames[i] = paramName;
        }
        return parameterNames;
    }
}