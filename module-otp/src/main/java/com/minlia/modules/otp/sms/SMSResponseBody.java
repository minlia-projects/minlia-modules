package com.minlia.modules.otp.sms;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chen junhan 569551869@qq.com
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SMSResponseBody{
    private String id;
}
