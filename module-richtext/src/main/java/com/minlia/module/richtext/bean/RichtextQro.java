package com.minlia.module.richtext.bean;

import com.minlia.module.data.entity.BaseQueryEntity;
import com.minlia.module.i18n.enums.LocaleEnum;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @author garen
 * @date 2017/7/20
 */
@Data
public class RichtextQro extends BaseQueryEntity {

    /**
     * 类型
     */
    @Size(max = 50)
    private String type;

    /**
     * 编码(唯一)
     */
    @Size(max = 50)
    private String code;

    /**
     * 主题
     */
    @Size(max = 200)
    private String subject;

    /**
     * 内容
     */
    private String content;

    /**
     * 语言
     */
    private LocaleEnum locale;

    /**
     * 备注
     */
    @Size(max = 500)
    private String remark;

    /**
     * 禁用标识
     */
    private Boolean disFlag;

}