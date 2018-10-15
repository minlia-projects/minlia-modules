package com.minlia.module.bible.bean.domain;

import com.minlia.module.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by will on 6/21/17.
 * 数据字典子项实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@JsonPropertyOrder({})
//@JsonIgnoreProperties(value = {})
//@ToString(of = {"id"})
//@EqualsAndHashCode(of = {"id","parentCode","code"},callSuper = true)
public class BibleItem extends AbstractEntity{

    private static final long serialVersionUID = 1L;

    /**
     * 父编码
     */
    private String parentCode;

    /**
     * 编码
     */
    private String code;

    /**
     * 值
     */
    private String value;

    /**
     * 说明
     */
    private String notes;

    /**
     * 排序
     */
    private Integer sorts;

    /**
     * 扩展字段1
     */
    private String attribute1;

    /**
     * 扩展字段2
     */
    private String attribute2;

    /**
     * 扩展字段3
     */
    private String attribute3;

}
