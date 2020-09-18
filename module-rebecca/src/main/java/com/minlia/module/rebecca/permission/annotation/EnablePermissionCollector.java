package com.minlia.module.rebecca.permission.annotation;

import com.minlia.module.rebecca.permission.listener.SysPermissionCollectorListener;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启动类
 * 使用方法：在SpringBoot的Application启动类上添加此注解即可
 *
 * @author garen
 * @version 201/7/15
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SysPermissionCollectorListener.class})
public @interface EnablePermissionCollector {
}