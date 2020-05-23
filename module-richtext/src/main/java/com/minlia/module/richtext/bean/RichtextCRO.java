package com.minlia.module.richtext.bean;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by garen on 2017/6/30.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RichtextCRO implements ApiRequestBody {

    /**
     * 类型
     */
    @NotBlank
    @Size(max = 50)
    private String type;

    /**
     * 编码(唯一)
     */
    @NotBlank
    @Size(max = 50)
    private String code;

    /**
     * 主题
     */
    @NotBlank
    @Size(max = 200)
    private String subject;

    /**
     * 内容
     */
    @NotBlank
    private String content;

    /**
     * 语言
     */
    @NotNull
    private LocaleEnum locale;

    /**
     * 备注
     */
    @NotBlank
    @Size(max = 500)
    private String remark;

    /**
     * 禁用标识
     */
    private Boolean disFlag;

    private boolean allLocale;

}
