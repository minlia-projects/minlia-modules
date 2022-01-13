package com.minlia.module.member.bean.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 企业-详情
 * </p>
 *
 * @author garen
 * @since 2020-09-08
 */
@Data
public class SysMemberInfoVo {

    @ApiModelProperty(value = "uid")
    private Long uid;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机")
    private String cellphone;

    @ApiModelProperty(value = "邀请码")
    private String inviteCode;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "注册时间")
    private LocalDateTime registerTime;

    @ApiModelProperty(value = "最后登陆IP")
    private String lastLoginIp;

    @ApiModelProperty(value = "最后登陆时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "钱包余额")
    private BigDecimal walletBalance;

    @ApiModelProperty(value = "积分余额")
    @JsonIgnore
    private Long integralBalance;

    @ApiModelProperty(value = "实名认证")
    private Boolean realName;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证")
    private String idNumber;

    @ApiModelProperty(value = "有设置二级密码")
    private Boolean hasSecondaryPassword;

    @ApiModelProperty(value = "有绑定支付宝")
    private Boolean hasBindAlipay;

}