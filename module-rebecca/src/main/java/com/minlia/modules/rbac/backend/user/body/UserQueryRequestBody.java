package com.minlia.modules.rbac.backend.user.body;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserQueryRequestBody {

    private Long id;

    private String referee;

    private String username;

    private Boolean enabled;

    private Boolean credentialsExpired;

    private Boolean expired;

    private Boolean locked;

    private String roleCode;

}