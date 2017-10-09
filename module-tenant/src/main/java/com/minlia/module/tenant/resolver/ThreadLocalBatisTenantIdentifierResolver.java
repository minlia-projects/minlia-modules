package com.minlia.module.tenant.batis.resolver;

public class ThreadLocalTenantIdentifierResolver implements TenantIdentifierResolver {

  private static final ThreadLocal<String> threadTenantIdentifier = new ThreadLocal<String>();

  @Override
  public String resolve() {
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

}
