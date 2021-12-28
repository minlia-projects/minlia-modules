package com.minlia.module.realname.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.realname.enums.SysAuthenticationStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 实名认证
 * </p>
 *
 * @author garen
 * @since 2021-12-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_real_name")
@ApiModel(value = "SysRealNameEntity对象", description = "实名认证")
public class SysRealNameEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "GUID")
    @TableField("uid")
    private Long uid;

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "身份证号码")
    @TableField("id_number")
    private String idNumber;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private SysAuthenticationStatusEnum status;

    @ApiModelProperty(value = "理由")
    @TableField("reason")
    private String reason;

    @ApiModelProperty(value = "身份证正面照片")
    @TableField("front_photo")
    private String frontPhoto;

    @ApiModelProperty(value = "身份证反面照片")
    @TableField("reverse_photo")
    private String reversePhoto;

    @ApiModelProperty(value = "身份证手持照片")
    @TableField("hold_photo")
    private String holdPhoto;

}