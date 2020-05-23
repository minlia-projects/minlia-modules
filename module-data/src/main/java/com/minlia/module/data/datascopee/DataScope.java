package com.minlia.module.data.datascopee;

import java.lang.annotation.*;


/**
 * 数据范围
 *
 * @author garen
 * @date 2018/4/17 下午2:40
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    DataScopeTypeEnum type() default DataScopeTypeEnum.OWN_LEVEL;

    /**
     * 限制范围的字段名称
     */
    String name() default "org_id";

    String[] value() default {};

}