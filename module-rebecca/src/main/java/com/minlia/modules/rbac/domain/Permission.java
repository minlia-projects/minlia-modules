package com.minlia.modules.rbac.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.entity.AbstractEntity;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Permission extends AbstractEntity {

  @JsonProperty
  private String code;
  @JsonProperty
  private String label;

  @JsonIgnore
  @ManyToMany(mappedBy = "permissions")
  private Set<Role> roles;

}
