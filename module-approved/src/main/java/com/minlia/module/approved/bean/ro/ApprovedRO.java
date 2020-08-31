package com.minlia.module.approved.bean.ro;

import com.minlia.module.approved.enumeration.ApprovedStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovedRO{

    @NotNull
    private Long id;

    /**
     * 状态
     */
    @NotNull
    private ApprovedStatusEnum status;

    /**
     * 备注
     */
    @Size(max = 3000)
    private String remark;
}