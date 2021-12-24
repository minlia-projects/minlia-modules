package com.minlia.module.member.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.member.enums.MemberAuthenticationStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 个人认证
 * </p>
 *
 * @author garen
 * @since 2020-09-08
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_personal_authentication")
@ApiModel(value = "SysPersonalAuthenticationEntity", description = "个人认证")
public class SysPersonalAuthenticationEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类目ID")
    @TableField("uid")
    private Long uid;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private MemberAuthenticationStatusEnum status;

    @ApiModelProperty(value = "理由")
    @TableField("reason")
    private String reason;

    @ApiModelProperty(value = "姓名")
    @TableField("fullname")
    private String fullname;

    @ApiModelProperty(value = "身份证号码")
    @TableField("id_number")
    private String idNumber;

    @ApiModelProperty(value = "身份证反面照片")
    @TableField("id_front_photo")
    private String idFrontPhoto;

    @ApiModelProperty(value = "身份证正面照片")
    @TableField("id_reverse_photo")
    private String idReversePhoto;

    @ApiModelProperty(value = "开户行")
    @TableField("bank_name")
    private String bankName;

    @ApiModelProperty(value = "银行账号")
    @TableField("bank_account_name")
    private String bankAccountName;

    @ApiModelProperty(value = "银行卡号")
    @TableField("bank_card_number")
    private String bankCardNumber;

}
