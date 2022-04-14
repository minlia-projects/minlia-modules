package com.minlia.module.rebecca.user.bean;

import com.minlia.module.common.annotation.SensitiveInfo;
import com.minlia.module.common.enums.SensitiveTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户-关系
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
@Data
@ApiModel(value = "SysUserRelationVo", description = "用户-关系")
public class SysUserRelationVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机号码")
    @SensitiveInfo(value = SensitiveTypeEnum.MOBILE_PHONE)
    private String cellphone;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "是否实名")
    private Boolean realName;

    @ApiModelProperty(value = "时间")
    private LocalDateTime time;

}