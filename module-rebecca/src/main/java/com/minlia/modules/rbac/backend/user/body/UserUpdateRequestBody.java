package com.minlia.modules.rbac.backend.user.body;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


/**
 * 用户-修改
 * Created by garen on 2017/8/8.
 */
@Data
public class UserUpdateRequestBody extends UserCreateRequestBody {

    @NotBlank
    private String guid;

}