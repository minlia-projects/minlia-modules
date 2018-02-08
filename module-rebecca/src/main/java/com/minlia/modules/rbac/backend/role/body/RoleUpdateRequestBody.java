package com.minlia.modules.rbac.backend.role.body;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
public class RoleUpdateRequestBody {

    @NotBlank
    @Size(max = 30)
    private String code;

    @NotBlank
    @Size(max = 50)
    private String label;

    @Size(max = 255)
    private String notes;

    private Boolean enabled;

}