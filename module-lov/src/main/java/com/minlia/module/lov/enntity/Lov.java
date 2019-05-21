package com.minlia.module.lov.enntity;

import com.minlia.module.data.entity.AbstractEntity;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@Data
public class Lov extends AbstractEntity {

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
     * 描述信息
     */
    @Size(max = 255)
    private String description;

    /**
     * 排序（升序）
     */
    private Byte sort;

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