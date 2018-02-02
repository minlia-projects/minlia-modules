package com.minlia.module.country.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.module.data.entity.AbstractEntity;
import lombok.*;


/**
 * 国家
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id","code","name"})
@EqualsAndHashCode(of = {"id","code","language"}, callSuper = true)
@JsonPropertyOrder({})
@JsonIgnoreProperties(value = {})
public class Country extends AbstractEntity {

    /**
     * 名称
     */
    @JsonProperty
    private String name;

    /**
     * 编码
     */
    @JsonProperty
    private String code;

    /**
     * 语言
     */
    @JsonProperty
    private String language;

    /**
     * 小图标
     */
    @JsonProperty
    private String smallIcon;

    /**
     * 大图标
     */
    @JsonProperty
    private String bigIcon;

    /**
     * 注释
     */
    private String notes;

}
