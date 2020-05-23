package com.minlia.modules.rebecca.bean.to;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 用户-授权
 * Created by garen on 2017/8/8.
 */
@Data
@Builder
@AllArgsConstructor
public class UserGrantTO implements ApiRequestBody {

    @NotBlank
    private String guid;

    @NotNull
    private Set<Long> roles;

}