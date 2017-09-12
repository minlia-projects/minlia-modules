package com.minlia.module.registry.v1.holder;

import com.minlia.cloud.holder.ContextHolder;

/**
 * Created by will on 8/27/17.
 */
public class RegistryHolder {

  /**
   * 获取到指定KEY的配置值
   */
  public static <T> T get(String key, Class<T> targetType) {
    return ContextHolder.getSystemProperty().getProperty(key, targetType);
  }

  public static String get(String key) {
    return RegistryHolder.get(key, String.class);
  }

}
