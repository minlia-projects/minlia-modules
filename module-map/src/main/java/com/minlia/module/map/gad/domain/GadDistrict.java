package com.minlia.module.map.gad.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.module.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({})
@JsonIgnoreProperties(value = {})
public class GadDistrict extends AbstractEntity {

    /**
     * 编码
     */
    @JsonProperty
    private String adcode;

    /**
     * 城市编码
     */
    @JsonProperty
    private String citycode;

    /**
     * 经纬度
     */
    @JsonProperty
    private String center;

    /**
     * 简称
     */
    @JsonProperty
    private String name;

    /**
     * 级别
     */
    @JsonProperty
    private String level;

    /**
     * 具体地址
     */
    @JsonProperty
    private String address;

    /**
     * 父类编码
     */
    private String parent;

    /**
     * 所有编码
     */
    private String fullcode;

}
