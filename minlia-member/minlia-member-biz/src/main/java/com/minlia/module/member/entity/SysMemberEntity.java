package com.minlia.module.member.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * <p>
 * 会员信息
 * </p>
 *
 * @author garen
 * @since 2021-12-28
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_member")
@ApiModel(value = "SysMemberEntity对象", description = "会员信息")
public class SysMemberEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableField("uid")
    private Long uid;

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "身份证号码")
    @TableField("id_number")
    private String idNumber;

    @ApiModelProperty(value = "实名认证")
    @TableField("real_name")
    private Boolean realName;

    @ApiModelProperty(value = "二级密码")
    @JsonIgnore
    @TableField("secondary_password")
    private String secondaryPassword;

    @ApiModelProperty(value = "余额")
    @TableField("balance")
    private BigDecimal balance;

    @ApiModelProperty(value = "链上地址")
    private String chainAddress;

}