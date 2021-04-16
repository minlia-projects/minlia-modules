package com.minlia.module.wechat.miniapp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.WithIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 微信小程序信息
 * </p>
 *
 * @author garen
 * @since 2021-04-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wx_ma_user_info")
@ApiModel(value = "WxMaUserInfoEntity对象", description = "微信小程序信息")
public class WxMaUserInfoEntity extends WithIdEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableField("uid")
    private Long uid;

    @ApiModelProperty(value = "手机号码")
    @TableField("phone_number")
    private String phoneNumber;

    @ApiModelProperty(value = "开放ID")
    @TableField("open_id")
    private String openId;

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "性别")
    @TableField("gender")
    private String gender;

    @ApiModelProperty(value = "语言")
    @TableField("language")
    private String language;

    @ApiModelProperty(value = "国家")
    @TableField("country")
    private String country;

    @ApiModelProperty(value = "省份")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "城市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "统一ID")
    @TableField("union_id")
    private String unionId;

    @ApiModelProperty(value = "应用ID")
    @TableField("appid")
    private String appid;

    @ApiModelProperty(value = "时间")
    @TableField("timestamp")
    private String timestamp;

    @ApiModelProperty(value = "头像")
    @TableField("avatarUrl")
    private String avatarurl;

}