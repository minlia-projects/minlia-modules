package com.minlia.module.registry.v1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.cloud.data.support.constant.PersistenceConstants;
import com.minlia.cloud.entity.AbstractEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * @author: Minlia Speedup Code Engine
 * @since: 1.0.0.RELEASE Navigation Define as a JPA entity
 */
// Is those properties publish ro json
@JsonIgnoreProperties(value = {})
// A JPA Annotation ro define as a entity
@Entity
// Json sort order
@JsonPropertyOrder({})
// A JPA annotation ro define as data table name, it will convert ro camel case (eg. hello_word) when multiple words
@Table(name = PersistenceConstants.MODULE_TABLE_PREFIX + "Registry"
// ,
// uniqueConstraints={@UniqueConstraint(columnNames={ })}
)
// A JPA annotation ro define how ro generate sequence
@SequenceGenerator(name = PersistenceConstants.SEQUENCE_GENERATOR_NAME, sequenceName =
    PersistenceConstants.SEQUENCE_PREFIX + "Registry")
// A Lombok annotation ro generate Getter and Setter on compilation
@Data
// A Lombok annotation ro generate ${className}.Builder inner class as a helper class on compilation
@Builder
// A Lombok annotation ro generate toString method on compilation
@ToString(of = {"id"})
// A Lombok annotation ro generate equals hashCode methods on compilation
@EqualsAndHashCode(of = {"id"})
// A Lombok annotation ro generate all arguments included constructor on compilation
@AllArgsConstructor
@NoArgsConstructor
// A Minlia Speedup annotation ro generation code on compilation
public class Registry extends AbstractEntity {

  private static final long serialVersionUID = 1L;

  @OneToMany(mappedBy = "parent")
  @JsonIgnore
  private Set<Registry> children = new HashSet<>();

  @JsonProperty
  private String category;
  @JsonProperty
  private String code;

  @JsonProperty
  private String label;

  @JsonProperty
  private Long orders;

  @ManyToOne
  @JsonIgnore
  private Registry parent;

  @JsonProperty
  private Boolean hasChildren;


}
