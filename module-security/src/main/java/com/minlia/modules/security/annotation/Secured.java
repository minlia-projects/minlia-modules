package com.minlia.modules.security.annotation;

import com.minlia.cloud.annotation.i18n.Localize;
import com.minlia.cloud.annotation.i18n.Localized;
import com.minlia.modules.security.enumeration.SecureStrategyEnum;
import org.springframework.cglib.core.Local;
import org.springframework.core.annotation.AliasFor;
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
    String[] label() default {};

    /**
     * 国际化
     * @return
     */
    @AliasFor(annotation = Localized.class)
    Localized[] localized() default {};

    /**
     * 安全策略
     * @return
     */
    SecureStrategyEnum stretegy() default SecureStrategyEnum.PUBLIC;

    /**
     * 在方法执行前执行权限检查
     *
     * @return
     */
    @AliasFor(annotation = PreAuthorize.class)
    PreAuthorize[] value() default {};

//    /**
//     * 在方法执行后执行权限检查
//     * @return
//     */
//    @AliasFor(annotation = PostAuthorize.class)
//    PostAuthorize[] after() default {};


}
