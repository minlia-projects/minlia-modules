package com.minlia.modules.rebecca.bean.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RoleCTO {

    @Min(0)
    private long parentId;

    @NotBlank
    @Size(max = 30)
    private String code;

    @NotBlank
    @Size(max = 50)
    private String label;

    @Size(max = 255)
    private String notes;

    private List<Long> permissions;

    @Size(max = 100)
    private String accessDomain;

    /**
     * 数据范围类型
     */
    private Integer dsType;

    /**
     * 数据范围自定义值
     */
    private Integer dsScope;

}