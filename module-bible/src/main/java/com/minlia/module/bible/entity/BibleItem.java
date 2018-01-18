package com.minlia.module.bible.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

/**
 * Created by will on 6/21/17.
 * 数据字典子项实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({})
@JsonIgnoreProperties(value = {})
@ToString(of = {"id"})
//@EqualsAndHashCode(of = {"id"},callSuper = true)
public class BibleItem {

  private static final long serialVersionUID = 1L;

  /**
   * 父编码
   */
  @JsonProperty
  private String parentCode;

  /**
   * 标签
   */
  @JsonProperty
  private String label;

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

  /**
   * 扩展字段
   */
  @JsonProperty
  private String attribute1;

  @JsonProperty
  private String attribute2;

  @JsonProperty
  private String attribute3;

  /**
   * 排序
   */
  private Integer sortOrder;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(referencedColumnName = "code")
//  @JsonProperty
//  private Bible bible;

  public BibleItem(String parentCode, String code, String label, String notes) {
    this.parentCode = parentCode;
    this.code = code;
    this.label = label;
    this.notes = notes;
  }

  public BibleItem(String parentCode, String code, String label, String notes,Integer sortOrder) {
    this.parentCode = parentCode;
    this.code = code;
    this.label = label;
    this.notes = notes;
    this.sortOrder = sortOrder;
  }

}
