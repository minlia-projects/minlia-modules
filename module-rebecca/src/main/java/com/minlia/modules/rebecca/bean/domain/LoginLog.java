package com.minlia.modules.rebecca.bean.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.modules.rebecca.enumeration.LoginStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


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

    private LocalDateTime time;

    private LoginStatusEnum status;

}
