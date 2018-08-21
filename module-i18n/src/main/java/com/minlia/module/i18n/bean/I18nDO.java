package com.minlia.module.i18n.bean;

import com.minlia.module.data.entity.AbstractEntity;
import lombok.Data;

@Data
public class I18nDO extends AbstractEntity {

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