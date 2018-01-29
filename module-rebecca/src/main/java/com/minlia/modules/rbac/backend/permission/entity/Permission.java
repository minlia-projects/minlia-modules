package com.minlia.modules.rbac.backend.permission.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.module.bible.entity.AbstractEntity;
import lombok.*;

/**
 * 权限点
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({})
@JsonIgnoreProperties(value = {})
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"},callSuper = true)
public class Permission extends AbstractEntity {

  /**
   * 权限点编码：account.creation
   */
  @JsonProperty
  private String code;

  /**
   * 国际化值请使用权限点 Lang.get(code);
   */
  @JsonProperty
  private String desc;

}
