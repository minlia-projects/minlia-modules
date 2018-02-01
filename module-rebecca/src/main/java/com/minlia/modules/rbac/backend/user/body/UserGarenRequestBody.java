package com.minlia.modules.rbac.backend.user.body;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 用户-授权
 * Created by garen on 2017/8/8.
 */
@Data
@Builder
@AllArgsConstructor
public class UserGarenRequestBody implements ApiRequestBody {

    @NotNull
    private Long id;

    @NotNull
    private Set<Long> roles;

}