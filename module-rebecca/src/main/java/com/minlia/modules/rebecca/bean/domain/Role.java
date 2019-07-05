package com.minlia.modules.rebecca.bean.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.module.data.entity.AbstractEntity;
import lombok.*;

/**
 * Created by will on 8/14/17.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({})
@JsonIgnoreProperties(value = {})
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"code"})
public class Role extends AbstractEntity {

    @JsonProperty
    private String code;

    @JsonProperty
    private String label;

    @JsonProperty
    private String notes;

    @JsonProperty
    private Boolean enabled;

}
