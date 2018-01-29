package com.minlia.modules.rbac.backend.user.body;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by 498391498 on 2017/10/18.
 */

/**
 * Created by garen on 2017/8/8.
 */
@ApiModel("用户-创建")
@Data
public class UserUpdateRequestBody extends UserCreateRequestBody {

    @NotNull
    @JsonProperty
    private String guid;

}