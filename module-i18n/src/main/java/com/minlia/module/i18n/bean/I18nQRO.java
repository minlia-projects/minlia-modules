package com.minlia.module.i18n.bean;

import com.minlia.module.data.entity.BaseQueryEntity;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * Created by garen on 2018/8/20.
 */
@Data
public class I18nQRO extends BaseQueryEntity {

    /**
     * 应用ID
     */
    @Size(max = 15)
    private String appid;

    /**
     * 键
     */
    @Size(max = 100)
    private String code;

    /**
     * 值
     */
    @Size(max = 1000)
    private String value;

    /**
     * 对应的语言
     */
    @Size(max = 15)
    private String language;

    /**
     * zh_CN or en_Us
     */
    @Size(max = 10)
    private String locale;

}
