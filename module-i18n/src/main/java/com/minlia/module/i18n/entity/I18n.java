package com.minlia.module.i18n.entity;

import com.minlia.module.data.entity.AuditableEntity;
import lombok.Data;

@Data
public class I18n extends AuditableEntity {

    /**
     * 应用ID
     */
    private String appid;

    /**
     * 键
     */
    private String code;

    /**
     * 值
     */
    private String value;

    /**
     * 对应的语言
     */
    private String language;

    /**
     * zh_CN or en_Us
     */
    private String locale;

}