package com.minlia.module.member.bean;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.member.enums.MemberAuthenticationStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 * 个人认证
 * </p>
 *
 * @author garen
 * @since 2020-09-08
 */
@Data
public class SysAuthenticationRo implements ApiRequestBody {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "状态")
    @NotNull
    private MemberAuthenticationStatusEnum status;

    @ApiModelProperty(value = "理由")
    @Size(max = 255)
    private String reason;



}