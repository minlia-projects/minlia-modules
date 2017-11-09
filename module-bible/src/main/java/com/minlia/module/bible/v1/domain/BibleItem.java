package com.minlia.module.bible.v1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.cloud.data.support.constant.PersistenceConstants;
import com.minlia.cloud.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
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
@Table(name = PersistenceConstants.MODULE_TABLE_PREFIX + "Bible_Item"
// ,
// uniqueConstraints={@UniqueConstraint(columnNames={ })}
)
// A JPA annotation to define how to generate sequence
@SequenceGenerator(name = PersistenceConstants.SEQUENCE_GENERATOR_NAME, sequenceName =
    PersistenceConstants.SEQUENCE_PREFIX + "Bible_Item")
// A Lombok annotation to generate Getter and Setter on compilation
@Data
// A Lombok annotation to generate ${className}.Builder inner class as a helper class on compilation
@Builder
// A Lombok annotation to generate toString method on compilation
@ToString(of = {"id"})
// A Lombok annotation to generate equals hashCode methods on compilation
//@EqualsAndHashCode(of = {"id"})
// A Lombok annotation to generate all arguments included constructor on compilation
@AllArgsConstructor
@NoArgsConstructor
// A Minlia Speedup annotation to generation code on compilation
@ApiModel(value = "字典项")
public class BibleItem extends AbstractEntity {

  private static final long serialVersionUID = 1L;

  /**
   * 标签
   */
  @ApiModelProperty(value = "标签, 名称", example = "男")
  @JsonProperty
  private String label;

  /**
   * 编码
   */
  @ApiModelProperty(value = "编码", example = "male")
  @JsonProperty
  private String code;


  /**
   * 说明
   */
  @JsonProperty
  @ApiModelProperty(value = "描述性说明", example = "男")
  private String notes;

  /**
   * 排序
   */
  @ApiModelProperty(value = "排序", example = "1")
  private Integer sortOrder;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name = "map_bible_items", inverseJoinColumns = @JoinColumn(name = "bible_id", referencedColumnName = "id"), joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
  @JsonIgnore
  private Bible bible;

  public BibleItem(Long id, String code, String label, String notes, Bible bible) {
    this.id = id;
    this.code = code;
    this.label = label;
    this.notes = notes;
    this.bible = bible;
  }

}
