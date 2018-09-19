package com.minlia.modules.qcloud.start.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Access Token
 * 所有场景默认采用 UTF-8 编码。
 * Access Token 必须缓存在磁盘，并定时刷新，建议每 1 小时 50 分钟刷新 Access Token，原 Access Token 两小时（7200S） 失效，期间两个 Token 都能使用。
 * 每次用户登录时必须重新获取 ticket。
 */
@Data
public class QcloudApiSignTicket implements Serializable {

    private String value;

    @JsonProperty(value = "expire_time")
    private long expireTime;

    @JsonProperty(value = "expire_in")
    private Integer expireIn;

}
