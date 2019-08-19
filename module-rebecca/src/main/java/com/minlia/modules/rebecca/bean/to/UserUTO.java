package com.minlia.modules.rebecca.bean.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.constant.LocalDateConstants;
import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import com.minlia.module.common.validation.Username;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import com.minlia.modules.rebecca.enumeration.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.time.LocalDateTime;


/**
 * 用户-修改
 * Created by garen on 2017/8/8.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUTO implements ApiRequestBody {

    private Long orgId;

    @NotBlank
    private String guid;

    @Username
    private String username;

    @Cellphone
    private String cellphone;

    @Email
    private String email;

    @Password
    private String password;

    private String defaultRole;

    private LocaleEnum defaultLocale;

    /**
     * 账号有效时间
     */
    private LocalDateTime accountEffectiveDate;

    /**
     * 凭证/密码有效时间
     */
    private LocalDateTime credentialsEffectiveDate;

    /**
     * 状态
     */
    private UserStatusEnum status;

}