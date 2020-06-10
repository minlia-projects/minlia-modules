package com.minlia.modules.rebecca.body;

import com.minlia.cloud.body.Body;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 用户登录成功后返回体
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserLogonResponseBody implements Body {


    private String token;
    private Long loginEffectiveDate;
    private String refreshToken;

}
