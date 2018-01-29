package com.minlia.modules.rbac.backend.user.body;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.cloud.constant.ValidationConstants;
import com.minlia.modules.rbac.backend.role.entity.Role;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by garen on 2017/8/8.
 */
@ApiModel("用户-创建")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestBody implements ApiRequestBody {

    @NotNull
    @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE, message = "Username have to be grater than 8 characters")
    @JsonProperty
    private String username;

    @NotNull
    @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE, message = "Password have to be grater than 8 characters")
    @JsonProperty
    private String password;

    private String referee;

    @JsonIgnore
    private Set<Role> roles;

}