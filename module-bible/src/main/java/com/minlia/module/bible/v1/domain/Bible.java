package com.minlia.module.bible.v1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.cloud.data.support.constant.PersistenceConstants;
import com.minlia.cloud.entity.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by will on 6/21/17.
 * 数据字典实体
 */

/**
 * @author: Minlia Speedup Code Engine
 * @since: 1.0.0.RELEASE Bible Define as a JPA entity
 */
// Is those properties publish to json
@JsonIgnoreProperties(value = {})
// A JPA Annotation to define as a entity
@Entity
// Json sort order
@JsonPropertyOrder({})
// A JPA annotation to define as data table name, it will convert to camel case (eg. hello_word) when multiple words
@Table(name = PersistenceConstants.MODULE_TABLE_PREFIX + "Bible"
// ,
// uniqueConstraints={@UniqueConstraint(columnNames={ })}
)
// A JPA annotation to define how to generate sequence
@SequenceGenerator(name = PersistenceConstants.SEQUENCE_GENERATOR_NAME, sequenceName =
    PersistenceConstants.SEQUENCE_PREFIX + "Bible")
// A Lombok annotation to generate Getter and Setter on compilation
@Data
// A Lombok annotation to generate ${className}.Builder inner class as a helper class on compilation
@Builder
// A Lombok annotation to generate toString method on compilation
@ToString(of = {"id"})
// A Lombok annotation to generate equals hashCode methods on compilation
@EqualsAndHashCode(of = {"id"})
// A Lombok annotation to generate all arguments included constructor on compilation
@AllArgsConstructor
@NoArgsConstructor
// A Minlia Speedup annotation to generation code on compilation
public class Bible extends AbstractEntity {

  private static final long serialVersionUID = 1L;

  /**
   * 编码
   */
  @ApiModelProperty(value = "编码", example = "gender")
  @JsonProperty
  private String code;

  /**
   * 标签
   */
  @ApiModelProperty(value = "标签, 名称", example = "性别")
  @JsonProperty
  private String label;


  @ApiModelProperty(value = "目标业务对象编码,如 appid的值", example = "1")
  @JsonProperty
  private String targetBusinessCode;

  /**
   * 说明
   */
  @JsonProperty
  @ApiModelProperty(value = "描述性说明", example = "用于性别选择")
  private String notes;

  /**
   * 排序
   */
  @ApiModelProperty(value = "排序", example = "1")
  private Integer sortOrder;


  @OneToMany(fetch = FetchType.LAZY)
  @OrderBy(value = "id")
  @JoinTable(name = "map_bible_items", joinColumns = @JoinColumn(name = "bible_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
  @JsonIgnore
  private Set<BibleItem> items;

  public Bible(Long id, String code, String label, String notes) {
    this.id = id;
    this.code = code;
    this.label = label;
    this.notes = notes;
  }

}
