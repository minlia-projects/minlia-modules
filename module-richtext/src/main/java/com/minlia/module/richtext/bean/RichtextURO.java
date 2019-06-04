package com.minlia.module.richtext.bean;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by garen on 2017/6/30.
 */
@Data
public class RichtextURO implements ApiRequestBody {

    @NotNull
    private Long id;

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
