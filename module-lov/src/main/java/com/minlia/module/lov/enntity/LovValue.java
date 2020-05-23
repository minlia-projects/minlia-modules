package com.minlia.module.lov.enntity;

import com.minlia.module.data.entity.AbstractEntity;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LovValue extends AbstractEntity {

    /**
     * 值集ID
     */
    @NotNull
    private Long lovId;

    @Transient
    @Size(max = 100)
    private String lovCode;

    /**
     * 父ID
     */
    @Size(max = 100)
    private String parentId;

    /**
     * 编码
     */
    @NotBlank
    @Size(max = 100)
    private String code;

    /**
     * 名称
     */
    @NotBlank
    @Size(max = 100)
    private String name;

    /**
     * 语言环境
     */
    @NotBlank
    @Size(max = 10)
    private String locale;

    /**
     * 描述信息
     */
    @Size(max = 255)
    private String description;

    /**
     * 排序（升序）
     */
    private Integer sort;

    /**
     * 禁用标记
     */
    private Boolean disFlag;

    /**
     * 删除标记
     */
    private Boolean delFlag;

    /**
     * 所属租户
     */
    private Integer tenantId;

}