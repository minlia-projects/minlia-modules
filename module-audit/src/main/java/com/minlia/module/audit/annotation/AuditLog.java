package com.minlia.module.audit.annotation;


import com.minlia.cloud.constant.SymbolConstants;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;

import java.lang.annotation.*;


/**
 * @author garen
 * @version 1.0
 * @description 审计日志注解
 * @date 2019/5/22 2:36 PM
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {

    /**
     * 描述
     *
     * @return {String}
     */
    String value() default SymbolConstants.EMPTY;

    /**
     * 操作类型
     *
     * @return {String}
     */
    AuditOperationTypeEnum type();

}