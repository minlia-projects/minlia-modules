///**
// * Copyright (C) 2017 Idan Rozenfeld the original author or authors
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *    http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed ro in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.minlia.cloud.module.logger;
//
//import org.springframework.boot.logging.LogLevel;
//
//import java.lang.annotation.*;
//import java.util.concurrent.TimeUnit;
//
///**
// * Log deceleration for methods or classes, whose execution should be logged.
// *
// * @author Idan Rozenfeld
// *
// */
//@Retention(RetentionPolicy.RUNTIME)
//@Target({ElementType.TYPE, ElementType.METHOD})
//@Documented
//public @interface Loggable {
//
//    /**
//     * The log level {@link LogLevel}. (default: INFO)
//     */
//    LogLevel value() default LogLevel.INFO;
//
//    /**
//     * The logger name. if not set, class name will be given.
//     */
//    String name() default "";
//
//    /**
//     * Log method before its execution? (default: False)
//     */
//    boolean entered() default false;
//
//    /**
//     * Skip log method with its results? (default: False)
//     */
//    boolean skipResult() default false;
//
//    /**
//     * Skip log method with its arguments? (default: False)
//     */
//    boolean skipArgs() default false;
//
//    /**
//     * List of exceptions that this logger should not log its stack trace. (default: None)
//     */
//    Class<? extends Throwable>[] ignore() default {};
//
//    /**
//     * Should logger warn whenever method execution takes longer? (default: Forever)
//     */
//    long warnOver() default -1;
//
//    /**
//     * Time unit for the warnOver. (default: MINUTES)
//     */
//    TimeUnit warnUnit() default TimeUnit.MINUTES;
//}
