package com.minlia.module.captcha.v1.body;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.body.Body;
import com.minlia.cloud.constant.ValidationConstants;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by will on 6/19/17.
 * 根据手机号码发送验证码请求体
 */
@Data
public class SecureCodeSendRequestBody  implements Body {

    /**
     * 根据手机号码发送验证码请求体
     */
    @NotNull
    @NotEmpty
    @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE)
    @JsonProperty
    private String username;
}
