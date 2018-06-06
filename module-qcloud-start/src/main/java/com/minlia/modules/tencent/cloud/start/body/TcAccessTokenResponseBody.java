package com.minlia.modules.tencent.cloud.start.body;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by garen on 2018/4/19.
 */
@Data
public class TcAccessTokenResponseBody extends TcResponseBaseBody{

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "expire_time")
    private long expireTime;

    @JsonProperty(value = "expire_in")
    private Integer expireIn;

}