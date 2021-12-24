package com.minlia.module.member;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author garen
 */
@Configuration
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class MemberAutoConfiguration {

}