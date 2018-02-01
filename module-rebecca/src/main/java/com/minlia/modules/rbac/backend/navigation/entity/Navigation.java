package com.minlia.modules.rbac.backend.navigation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.modules.rbac.backend.navigation.enumeration.NavigationType;
import lombok.*;

import java.util.Set;

/**
 * 导航实体
 * Created by will on 6/17/17.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({})
@JsonIgnoreProperties(value = {})
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"},callSuper = true)
public class Navigation extends AbstractEntity {

    @JsonProperty
    private NavigationType type;

    /**
     * 名称
     */
    @JsonProperty
    private String name;

    /**
     * 图标
     */
    @JsonProperty
    private String icon;

    /**
     * 路由
     */
    @JsonProperty
    private String state;

    /**
     * 顺序
     */
    @JsonProperty
    private Integer orders;

    @JsonProperty
    private Boolean display;

    /**
     * 父ID
     */
    @JsonProperty
    private Long parentId;

    @JsonProperty
    private Set<Navigation> children;

}
