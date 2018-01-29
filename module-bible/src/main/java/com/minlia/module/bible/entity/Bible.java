package com.minlia.module.bible.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
//@EqualsAndHashCode(of = {"id"},callSuper = true)
public class Bible extends AbstractEntity {

  private static final long serialVersionUID = 1L;

  /**
   * 编码
   */
  @JsonProperty
  private String code;

  /**
   * 标签
   */
  @JsonProperty
  private String label;

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

//  @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "bible")
//  @OrderBy(value = "sortOrder")
//  @JsonProperty
//  @JsonIgnoreProperties("bible")
//  private Set<BibleItem> items;

//  public Bible(Long id, String code, String label, String value, String notes) {
//    this.id = id;
//    this.code = code;
//    this.label = label;
//    this.value = value;
//    this.notes = notes;
//  }

}
