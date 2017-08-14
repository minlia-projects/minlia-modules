/*
 * Copyright (c) 2017  The Hyve and respective contributors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the file LICENSE in the root of this repository.
 */

package com.minlia.modules.starter.permission.collector.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Mark a controller method as public.
 * The associated url with be accessible for any user,
 * even unauthorised users.
 * 权限注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Permissions {

    /**
     * 权限Code
     * @return
     */
    String code();

    /**
     * 权限描述
     * @return
     */
    String description();
}
