package com.minlia.modules.rebecca.bean.to;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
public class PermissionUTO implements ApiRequestBody {

    @NotNull
    private Long id;

    @NotBlank
    private String label;

    private Boolean enabled;

}
