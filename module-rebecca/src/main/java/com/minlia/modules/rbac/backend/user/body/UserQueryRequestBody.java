package com.minlia.modules.rbac.backend.user.body;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserQueryRequestBody {

    private Long id;

    private String referral;

    private String username;

    private String cellphone;

    private String email;

    private Boolean enabled;

    private Boolean locked;

    private String roleCode;

}