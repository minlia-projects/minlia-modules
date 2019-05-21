package com.minlia.modules.security;

import com.minlia.module.common.property.MinliaValidProperties;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by will on 8/14/17.
 *
 * 需要考虑当前模块的相关类扫描路径
 */
@Configuration
@EnableConfigurationProperties(value = {MinliaValidProperties.class})
public class ModuleSecurityAutoConfiguration {

}
