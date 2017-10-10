package com.minlia.module.tenant.resolver;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class ThreadLocalBatisTenantIdentifierResolver implements BatisTenantIdentifierResolver,CurrentTenantIdentifierResolver {

  private static final ThreadLocal<String> threadTenantIdentifier = new ThreadLocal<String>();
  private static final ThreadLocal<Boolean> bypass= new ThreadLocal<Boolean>();

  public static Boolean getBypass() {
    return bypass.get();
  }

  public static void setBypass(Boolean bypass1) {
    bypass.set(bypass1);
  }



  @Override
  public String resolveCurrentTenantIdentifier() {
    String currentTenantIdentifier = getCurrentTenantIdentifier();
    return currentTenantIdentifier == null ? "" : currentTenantIdentifier;
  }

  /**
   * 设置当前租户标识符
   */
  public static void setCurrentTenantIdentifier(String tenantIdentifier) {
    threadTenantIdentifier.set(tenantIdentifier);
  }

  /**
   * 获取当前租户标识符
   */
  public static String getCurrentTenantIdentifier() {
    return threadTenantIdentifier.get();
  }


  public static final void remove() {
    threadTenantIdentifier.remove();
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return true;
  }
}
