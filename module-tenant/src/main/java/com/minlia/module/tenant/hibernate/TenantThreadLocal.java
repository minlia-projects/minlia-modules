//package com.minlia.module.tenant.hibernate;
//
//public class TenantThreadLocal {
//
//  private static final ThreadLocal<String> tenantThreadLocal = new ThreadLocal<String>();
//  private static final String DEFAULT_TENANT = "at";
//
//  public static String getTenant() {
//    String tenant = tenantThreadLocal.get();
//    return tenant != null ? tenant : DEFAULT_TENANT;
//  }
//
//  public static void setTenant(String tenantCode) {
//    tenantThreadLocal.set(tenantCode);
//  }
//
//  public static void remove() {
//    tenantThreadLocal.remove();
//  }
//}