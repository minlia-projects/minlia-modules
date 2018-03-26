package com.minlia.module.richtext.v1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.boot.persistence.constant.PersistenceConstants;
import com.minlia.module.persistence.entity.AbstractAuditingEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = PersistenceConstants.MODULE_TABLE_PREFIX + "richtext",uniqueConstraints={@UniqueConstraint(columnNames={"type","code"})})
//@SequenceGenerator(name = PersistenceConstants.SEQUENCE_GENERATOR_NAME, sequenceName = PersistenceConstants.SEQUENCE_PREFIX + "richtext")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})

@JsonPropertyOrder({})
@JsonIgnoreProperties(value = {})

/**
 * 富文本
 */
public class Richtext extends AbstractAuditingEntity {

    private String type;

    @JsonProperty
    private String title;

    @JsonProperty
    private String code;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false,columnDefinition = "mediumtext")
    @JsonProperty
    private String content;

    @JsonProperty
    private String note;

}
