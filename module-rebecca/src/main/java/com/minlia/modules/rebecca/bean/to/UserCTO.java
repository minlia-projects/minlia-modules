package com.minlia.modules.rebecca.bean.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.constant.LocalDateConstants;
import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import com.minlia.module.common.validation.Username;
import com.minlia.modules.rebecca.enumeration.RegistrationMethodEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by garen on 2017/8/8.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCTO implements ApiRequestBody {

//    @ApiModelProperty(value = "注册方式")
//    @NotNull(message = "注册方式不能为空")
//    private RegistrationMethodEnum method;

    @Username
    private String username;

    @Cellphone
    private String cellphone;

    @Email
    private String email;

    @NotBlank
    @Password
    private String password;

    @NotNull
    private Long defaultRole;

    private Set<Long> roles;

    @JsonIgnore
    private String referral;

    /**
     * 昵称
     */
    @Size(min = 1, max = 11)
    private String nickname;

    /**
     * 账号有效时间
     */
    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime accountEffectiveDate;

    /**
     * 凭证/密码有效时间
     */
    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime credentialsEffectiveDate;

}