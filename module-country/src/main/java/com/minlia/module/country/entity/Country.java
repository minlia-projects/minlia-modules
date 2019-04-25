package com.minlia.module.country.entity;

import com.minlia.module.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 国家
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Country extends AbstractEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 语言
     */
    private String language;

    /**
     * 小图标
     */
    private String smallIcon;

    /**
     * 大图标
     */
    private String bigIcon;

    /**
     * 注释
     */
    private String notes;

}
