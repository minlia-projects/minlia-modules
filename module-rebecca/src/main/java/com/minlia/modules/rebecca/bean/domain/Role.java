package com.minlia.modules.rebecca.bean.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private Long parentId;

    private String code;

    private String label;

    /**
     * 数据范围类型
     */
    private Integer dsType;

    /**
     * 数据范围自定义值
     */
    private Integer dsScope;

    private String notes;

    private Boolean enabled;

    private String accessDomain;

}
