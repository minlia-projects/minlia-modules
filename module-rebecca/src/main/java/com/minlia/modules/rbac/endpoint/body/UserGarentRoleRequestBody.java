package com.minlia.modules.rbac.endpoint.body;

import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by garen on 2017/8/8.
 */
@ApiModel("用户授权")
@Data
public class UserGarentRoleRequestBody implements ApiRequestBody {

    @NotNull
    private Long userId;

    @NotBlank
    private String roleCode;

}