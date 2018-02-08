package com.minlia.module.bible.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.module.data.entity.AbstractEntity;
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
public class Bible extends AbstractEntity {

  private static final long serialVersionUID = 1L;

  /**
   * 编码
   */
  @JsonProperty
  private String code;

  /**
   * 值
   */
  @JsonProperty
  private String value;

  /**
   * 说明
   */
  @JsonProperty
  private String notes;

}
