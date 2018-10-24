package com.minlia.module.captcha.bean.domain;

import com.minlia.module.data.entity.WithIdEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 系统验证码实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Captcha extends WithIdEntity {

    /**
     * 手机号码
     */
    private String cellphone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 验证码内容
     */
    private String code;

    /**
     * 是否已被使用
     */
    private Boolean used = false;

    /**
     * 是否锁定(用于风控) TODO
     */
    private Boolean locked;

    /**
     * 验证有效时间
     */
    private Date effectiveTime;

    /**
     * 连续验证失败次数
     */
    private Integer failureCount;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 每天发送次数 TODO
     */
    private Integer dayTimes;

    /**
     * 每月发送次数 TODO
     */
    private Integer monthTimes;

}