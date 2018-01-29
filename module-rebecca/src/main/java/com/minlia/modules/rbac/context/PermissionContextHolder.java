package com.minlia.modules.rbac.context;

import java.util.Set;

/**
 * Created by will on 8/23/17.
 */
public class PermissionContextHolder {
  private final static ThreadLocal<PermissionContext> context = new ThreadLocal<PermissionContext>();

  public static Boolean add(String permission) {
    PermissionContext permissionContext=context.get();
    if(null == permissionContext){
      context.set(new PermissionContext());
    }
    return context.get().addPermission(permission);
  }

  public static Set<String> get() {
    return context.get().getPermissions();
  }


}
