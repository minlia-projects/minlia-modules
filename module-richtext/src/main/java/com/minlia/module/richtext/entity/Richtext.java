package com.minlia.module.richtext.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.module.data.entity.AbstractEntity;
import lombok.*;


/**
 * 富文本
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@JsonPropertyOrder({})
@JsonIgnoreProperties(value = {})
public class Richtext extends AbstractEntity {

    /**
     * 类型
     */
    private String type;

    /**
     * 编码(唯一)
     */
    private String code;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 备注
     */
    private String note;

}
