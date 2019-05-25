package com.minlia.module.lov.enntity;

import com.minlia.module.data.entity.AbstractEntity;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LovValue extends AbstractEntity {

    /**
     * 值集ID
     */
    @NotNull
    private Long lovId;

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 编码
     */
    @NotNull
    @Size(max = 100)
    private String code;

    /**
     * 名称
     */
    @NotNull
    @Size(max = 100)
    private String name;

    /**
     * 语言环境
     */
    @NotBlank
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