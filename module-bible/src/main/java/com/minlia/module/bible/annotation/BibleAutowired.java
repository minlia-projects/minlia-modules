package com.minlia.module.bible.annotation;

import java.lang.annotation.*;


/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/5/22 2:36 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BibleAutowired {

    /**
     * 描述
     *
     * @return {String}
     */
    String type() default "";

//    /**
//     * 操作类型
//     *
//     * @return {String}
//     */
//    OperationTypeEnum operationType() default OperationTypeEnum.UNKNOWN;

}