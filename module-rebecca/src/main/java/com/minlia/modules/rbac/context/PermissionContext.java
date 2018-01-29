package com.minlia.modules.rbac.context;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by will on 8/23/17.
 */
public class PermissionContext {

  private Set<String> permissions = Sets.newHashSet();

  public Boolean addPermission(String permission){
    return permissions.add(permission);
  }
  public Set<String> getPermissions() {
    return permissions;
  }

  public void setPermissions(Set<String> permissions) {
    this.permissions = permissions;
  }

}
