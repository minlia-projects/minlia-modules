package com.minlia.modules.rbac.v1.openapi.resetpassword;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.cloud.constant.ValidationConstants;
import com.minlia.modules.rbac.validation.UniqueUsername;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by will on 6/19/17.
 */
@Data
public class ResetPasswordRequestBody implements ApiRequestBody{
    /**
     * 用户名
     */
    @NotNull
    @UniqueUsername(message = "Username already exists")//TODO i18n internationalization
    @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE,message = "Username have to be grater than 8 characters")
    @Column(unique = true)
    @JsonProperty
    private String username;

    /**
     * 验证码
     */
    @JsonProperty
    private String code;

    /**
     * 密码
     */
    @NotNull
    @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE,message = "Password have to be grater than 8 characters")
    @JsonProperty
    private String newPassword;



}
