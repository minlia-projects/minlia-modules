package com.minlia.modules.rebecca.bean.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.module.data.entity.AuditableEntity;
import com.minlia.modules.rebecca.enumeration.NavigationType;
import lombok.*;

import java.util.List;

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
public class Navigation extends AuditableEntity {

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 类型
     */
    private NavigationType type;

    /**
     * 名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路由
     */
    private String state;

    /**
     * 顺序
     */
    private Integer orders;

    private Boolean display;

    private List<Navigation> children;

}
