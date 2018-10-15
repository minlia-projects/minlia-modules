package com.minlia.module.captcha.bean.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.module.data.entity.WithIdEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * System validation token class
 * 系统短信验证码实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Captcha extends WithIdEntity {

    /**
     * 手机号码
     */
    @JsonProperty
    private String cellphone;

    /**
     * 验证码内容
     */
    @JsonProperty
    private String code;

    /**
     * 是否已被使用
     */
    @JsonProperty
    private Boolean used = false;

    /**
     * 是否锁定(用于风控) TODO
     */
    @JsonProperty
    private Boolean locked;

    /**
     * 验证有效时间
     */
    @JsonProperty
    private Date effectiveTime;

    /**
     * 连续验证失败次数
     */
    @JsonProperty
    private Integer failureCount;

    /**
     * 发送时间
     */
    @JsonProperty
    private Date sendTime;

    /**
     * 每天发送次数 TODO
     */
    @JsonProperty
    private Integer dayTimes;

    /**
     * 每月发送次数 TODO
     */
    @JsonProperty
    private Integer monthTimes;

}