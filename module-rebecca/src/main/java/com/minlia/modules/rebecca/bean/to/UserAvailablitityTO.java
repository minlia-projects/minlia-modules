package com.minlia.modules.rebecca.bean.to;

import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Username;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;

/**
 * Created by garen on 2017/7/24.
 * 用户有效性验证请求体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAvailablitityTO {

//    @ApiModelProperty(value = "注册方式")
//    @NotNull(message = "注册方式不能为空")
//    private RegistrationMethodEnum method;

    @Username
    private String username;

    @Cellphone
    private String cellphone;

    @Email
    private String email;

}
