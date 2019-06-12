package com.minlia.modules.rbac.bean.qo;

import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserQO extends QueryRequest {

    private Long id;

    private String guid;

    private String username;

    private String cellphone;

    private String email;

    private Boolean locked;

    private String referral;

    private Boolean enabled;

    private String roleCode;

    private List<String> roleCodes;

}