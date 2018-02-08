package com.minlia.module.bible.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.module.data.entity.AbstractEntity;
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
@EqualsAndHashCode(of = {"id","parentCode","code"},callSuper = true)
public class BibleItem extends AbstractEntity{

    private static final long serialVersionUID = 1L;

    /**
     * 父编码
     */
    @JsonProperty
    private String parentCode;

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
     * 排序
     */
    @JsonProperty
    private Integer sorts;

    /**
     * 扩展字段
     */
    @JsonProperty
    private String attribute1;

    @JsonProperty
    private String attribute2;

    @JsonProperty
    private String attribute3;

}
