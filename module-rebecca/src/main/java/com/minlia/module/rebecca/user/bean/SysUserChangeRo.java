package com.minlia.module.rebecca.user.bean;

import com.minlia.aliyun.green.annotation.Antispam;
import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 用户-修改
 *
 * @author garen
 * @date 2017/8/8
 */
@Data
public class SysUserChangeRo implements ApiRequestBody {

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "昵称")
    //@Antispam
    private String nickname;

}