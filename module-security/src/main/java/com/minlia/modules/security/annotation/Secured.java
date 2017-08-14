package com.minlia.modules.security.annotation;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 添加 Public 可以访问的资源
 * 添加 AuthorizedUser 可以访问的资源
 * <p>
 * 结合Spring Security的以下注解进行权限控制
 * </p>
 * <p>
 * PreAuthorized
 * PostAuthorized
 * </p>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Secured {

    /**
     * 权限Code
     *
     * @return
     */
    PreAuthorize pre();

    /**
     * 权限描述
     *
     * @return
     */
    PostAuthorize post();


}
