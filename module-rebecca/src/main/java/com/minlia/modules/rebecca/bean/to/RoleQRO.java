package com.minlia.modules.rebecca.bean.to;

import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleQRO extends QueryRequest {

    private Long id;

    private String createBy;

    private String lastModifiedBy;

    private LocalDateTime createDate;

    private LocalDateTime lastModifiedDate;

    @Min(0)
    private long parentId;

    @Size(max = 30)
    private String code;

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

    private Boolean enabled;

}