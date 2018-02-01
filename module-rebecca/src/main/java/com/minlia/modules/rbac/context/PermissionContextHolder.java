//package com.minlia.modules.rbac.context;
//
//import java.util.Set;
//
///**
// * Created by will on 8/23/17.
// */
//public class PermissionContextHolder {
//  private final static ThreadLocal<PermissionContext> CONTEXT = new ThreadLocal<PermissionContext>();
//
//  public static Boolean add(String permission) {
//    PermissionContext permissionContext=CONTEXT.get();
//    if(null == permissionContext){
//      CONTEXT.set(new PermissionContext());
//    }
//
//    return CONTEXT.get().addPermission(permission);
//  }
//
//  public static Set<String> get() {
//    return CONTEXT.get().getPermissions();
//  }
//
//
//}
