package com.minlia.modules.rebecca.bean.domain;

import com.minlia.module.data.entity.AbstractEntity;
import lombok.Data;

@Data
public class Organization extends AbstractEntity {

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 禁用标识
     */
    private Boolean disFlag;

    /**
     * 删除标识
     */
    private Boolean delFlag;

    /**
     * 租户ID
     */
    private Integer tenantId;

}