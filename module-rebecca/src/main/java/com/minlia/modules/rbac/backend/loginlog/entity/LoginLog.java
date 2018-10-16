package com.minlia.modules.rbac.backend.loginlog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.modules.rbac.enumeration.LoginStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by garen on 2018/05/27.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({})
@JsonIgnoreProperties(value = {})
public class LoginLog {

    private String username;

    private String password;

    private String ipAddress;

    private Date time;

    private LoginStatusEnum status;

}
