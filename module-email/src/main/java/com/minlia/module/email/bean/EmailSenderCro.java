package com.minlia.module.email.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * <p>
 * 邮件-发现人
 * </p>
 *
 * @author garen
 * @since 2021-01-11
 */
@Data
@ApiModel(value = "EmailFromCro")
public class EmailSenderCro {

    @ApiModelProperty(value = "代码")
    @NotBlank
    @Size(max = 20)
    private String code;

    @ApiModelProperty(value = "主机")
    @NotBlank
    @Size(max = 20)
    private String host;

    @ApiModelProperty(value = "端口")
    @NotNull
    @Digits(integer = 5, fraction = 0)
    private Integer port;

    @ApiModelProperty(value = "用户名")
    @NotBlank
    @Email
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank
    @Size(max = 20)
    private String password;

    @ApiModelProperty(value = "回件人")
    @NotBlank
    @Email
    private String replyTo;

    @ApiModelProperty(value = "'协议'")
    @NotBlank
    @Size(max = 20)
    private String protocol;

    @ApiModelProperty(value = "描述")
    @Size(max = 50)
    private String desc;

    @ApiModelProperty(value = "是否默认")
    private Boolean defFlag;

    @ApiModelProperty(value = "是否禁用")
    private Boolean disFlag;

    @ApiModelProperty(value = "是否删除")
    private Boolean delFlag;

}