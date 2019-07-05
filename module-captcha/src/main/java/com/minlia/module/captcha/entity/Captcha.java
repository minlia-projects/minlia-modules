package com.minlia.module.captcha.entity;

import com.minlia.module.data.entity.AbstractEntity;
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
public class Captcha extends AbstractEntity {

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
     * 发送时间
     */
    private Date sendTime;

    /**
     * 验证有效时间
     */
    private Date effectiveTime;

    /**
     * 倒计时
     */
    private Integer countdown;

    /**
     * 连续验证失败次数
     */
    private Integer failureCount;

    /**
     * 是否锁定(用于风控) TODO
     */
    private Boolean locked;

    /**
     * 锁定时间
     */
    private Date lockTime;





    /**
     * 每天发送次数 TODO
     */
    private Integer dayTimes;

    /**
     * 每月发送次数 TODO
     */
    private Integer monthTimes;

}