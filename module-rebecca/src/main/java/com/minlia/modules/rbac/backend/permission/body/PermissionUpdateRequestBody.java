package com.minlia.modules.rbac.backend.permission.body;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
public class PermissionUpdateRequestBody implements ApiRequestBody {

    @NotNull
    private Long id;

    @NotBlank
    private String label;

    private Boolean enabled;

}
