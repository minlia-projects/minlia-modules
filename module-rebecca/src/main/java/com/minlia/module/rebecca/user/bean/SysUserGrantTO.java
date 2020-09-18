package com.minlia.module.rebecca.user.bean;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * 用户-授权
 *
 * @author garen
 * @date 2017/8/8
 */
@Data
public class SysUserGrantTO implements ApiRequestBody {

    @NotBlank
    private Long uid;

    @NotEmpty
    private Set<Long> roles;

}