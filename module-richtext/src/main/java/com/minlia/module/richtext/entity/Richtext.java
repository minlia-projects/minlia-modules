package com.minlia.module.richtext.entity;

import com.minlia.module.data.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 富文本
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Richtext extends AuditableEntity {

    /**
     * 类型
     */
    private String type;

    /**
     * 编码(唯一)
     */
    private String code;

    /**
     * 主题
     */
    private String subject;

    /**
     * 内容
     */
    private String content;

    /**
     * 语言
     */
    private String locale;

    /**
     * 备注
     */
    private String remark;

    /**
     * 禁用标识
     */
    private Boolean disFlag;

}
