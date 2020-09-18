package com.minlia.module.captcha.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 验证码
 * </p>
 *
 * @author garen
 * @since 2020-08-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_captcha")
@ApiModel(value = "SysCaptchaEntity对象", description = "验证码")
public class CaptchaEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机号码")
    @TableField("cellphone")
    private String cellphone;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "验证码")
    @TableField("vcode")
    private String vcode;

    @ApiModelProperty(value = "发送时间")
    @TableField("send_time")
    private LocalDateTime sendTime;

    @ApiModelProperty(value = "有效时间")
    @TableField("effective_time")
    private LocalDateTime effectiveTime;

    @ApiModelProperty(value = "锁定时间")
    @TableField("lock_time")
    private LocalDateTime lockTime;

    @ApiModelProperty(value = "验证失败次数")
    @TableField("failure_count")
    private Integer failureCount;

    @ApiModelProperty(value = "锁定标识")
    @TableField("lock_flag")
    private Boolean lockFlag;

    @ApiModelProperty(value = "使用标识")
    @TableField("use_flag")
    private Boolean useFlag;

    @ApiModelProperty(value = "倒计时")
    @TableField(exist = false)
    private Integer countdown;

}