package com.minlia.module.i18n.attribution.entity;

import com.minlia.module.data.entity.AbstractEntity;
import lombok.Data;

@Data
public class I18nAttribution extends AbstractEntity {
    /**
     * 主键
     */
    private Long id;

    /**
     * 归属code
     */
    private String attributionCode;

    /**
     * 国际化code
     */
    private String i18nCode;
}