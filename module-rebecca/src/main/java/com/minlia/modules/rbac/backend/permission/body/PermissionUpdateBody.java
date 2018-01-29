package com.minlia.modules.rbac.backend.permission.body;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PermissionUpdateBody implements ApiRequestBody {

    @NotNull
    private Long id;

    private String desc;

}
