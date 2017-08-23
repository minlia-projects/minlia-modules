package com.minlia.modules.rbac.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.entity.AbstractEntity;
import com.minlia.module.language.v1.domain.Language;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@ApiModel(value = "权限点")
public class Permission extends AbstractEntity {

  @ApiModelProperty(value = "权限点编码",example = "account.creation")
  @JsonProperty
  private String code;

  @JsonProperty
  private String label;

  @JsonIgnore
  @ManyToMany
  @JoinTable(name = "map_role_permissions",inverseJoinColumns  = @JoinColumn(name = "role_id", referencedColumnName = "id"),joinColumns  = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
  private Set<Role> roles;


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}
