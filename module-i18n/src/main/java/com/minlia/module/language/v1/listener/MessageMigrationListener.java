/*
 * Copyright © 2016 Minlia (http://oss.minlia.com/license/framework/2016)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.minlia.module.language.v1.listener;

import com.minlia.module.language.v1.messagesource.InitializableMessageSource;
import com.minlia.module.language.v1.messagesource.jdbc.JdbcMessageProvider;
import javax.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * 系统启动之后进行语言迁移
 */
@Slf4j
@Component
@Priority(value = Ordered.HIGHEST_PRECEDENCE+1)
public class MessageMigrationListener implements ApplicationListener<ApplicationReadyEvent>,
    EnvironmentAware {

  @Autowired
  private JdbcMessageProvider jdbcMessageProvider;

  @Autowired
  InitializableMessageSource initializableMessageSource;

  private RelaxedPropertyResolver propertyResolver;

  @Override
  public void setEnvironment(Environment environment) {
    this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.application.");
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    log.debug("开始国际化语言初始化");

    try {
      initializableMessageSource.setAutoInitialize(true);
      initializableMessageSource.afterPropertiesSet();
    } catch (Exception e) {
      log.debug("afterPropertiesSet With exception {}", e.getMessage());
    }

    log.debug("完成国际化语言初始化");
  }

  private boolean decideIfImport(String basename) {
    return true;
  }
}
