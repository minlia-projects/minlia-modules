package com.minlia.module.advertisement.bean.domain;

import com.minlia.module.advertisement.enumeration.AdTypeEnum;
import com.minlia.module.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 广告
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement extends AbstractEntity {

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 封面
     */
    private String cover;

    /**
     * 链接类型（page，url）
     */
    private AdTypeEnum type;

    /**
     * 链接地址
     */
    private String path;

    /**
     * 备注
     */
    private String notes;

    /**
     * 是否启用
     */
    private Boolean enabled;

}
