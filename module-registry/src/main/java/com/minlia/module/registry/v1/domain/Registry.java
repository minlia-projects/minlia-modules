package com.minlia.module.registry.v1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.boot.persistence.constant.PersistenceConstants;
import com.minlia.module.persistence.entity.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * @author: Minlia Speedup Code Engine
 * @since: 1.0.0.RELEASE
 * Navigation Define as a JPA entity
 */
// Is those properties publish to json
@JsonIgnoreProperties(value = {})
// A JPA Annotation to define as a entity
@Entity
// Json sort order
@JsonPropertyOrder({})
// A JPA annotation to define as data table name, it will convert to camel case (eg. hello_word) when multiple words
@Table(name = PersistenceConstants.MODULE_TABLE_PREFIX + "Registry"
// ,
// uniqueConstraints={@UniqueConstraint(columnNames={ })}
)
// A JPA annotation to define how to generate sequence
@SequenceGenerator(name = PersistenceConstants.SEQUENCE_GENERATOR_NAME, sequenceName = PersistenceConstants.SEQUENCE_PREFIX + "Registry")
// A Lombok annotation to generate Getter and Setter on compilation
@Data
// A Lombok annotation to generate ${className}.Builder inner class as a helper class on compilation
@Builder
// A Lombok annotation to generate toString method on compilation
@ToString(exclude = {"children","parent"})
// A Lombok annotation to generate equals hashCode methods on compilation
@EqualsAndHashCode(exclude = {"children","parent"})
// A Lombok annotation to generate all arguments included constructor on compilation
@AllArgsConstructor
@NoArgsConstructor
// A Minlia Speedup annotation to generation code on compilation
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
