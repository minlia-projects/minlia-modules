package com.minlia.modules.rbac.bean.to;

import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Username;
import com.minlia.modules.rbac.enumeration.RegistrationMethodEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

/**
 * Created by garen on 2017/7/24.
 * 用户有效性验证请求体
 */
@Data
public class UserAvailablitityTO {

    @ApiModelProperty(value = "注册方式")
    @NotNull(message = "注册方式不能为空")
    private RegistrationMethodEnum method;

    @Username
    private String username;

    @Cellphone
    private String cellphone;

    @Email
    private String email;

}
