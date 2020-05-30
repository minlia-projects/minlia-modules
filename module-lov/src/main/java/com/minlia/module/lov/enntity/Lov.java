package com.minlia.module.lov.enntity;

import com.minlia.module.data.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lov extends AuditableEntity {

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