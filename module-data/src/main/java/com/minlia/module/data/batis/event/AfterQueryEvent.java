package com.minlia.module.data.batis.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author will
 */
public class AfterQueryEvent extends ApplicationEvent {

  public AfterQueryEvent(Object source) {
    super(source);
  }
}
