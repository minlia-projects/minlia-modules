package com.minlia.module.i18n.bean;

import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by garen on 2018/8/20.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class I18nQO extends QueryRequest {

    //唯一标识
    private long id;

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
