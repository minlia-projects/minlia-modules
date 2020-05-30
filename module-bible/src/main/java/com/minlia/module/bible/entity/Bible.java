package com.minlia.module.bible.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.module.data.entity.AuditableEntity;
import lombok.*;

/**
 * Created by will on 6/21/17.
 * 数据字典实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({})
@JsonIgnoreProperties(value = {})
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id","code"},callSuper = true)
public class Bible extends AuditableEntity {

  private static final long serialVersionUID = 1L;

  /**
   * 编码
   */
  private String code;

  /**
   * 值
   */
  private String value;

  /**
   * 说明
   */
  private String notes;

}
