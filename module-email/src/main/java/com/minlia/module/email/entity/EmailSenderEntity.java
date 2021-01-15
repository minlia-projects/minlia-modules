package com.minlia.module.email.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 邮件-发件人
 * </p>
 *
 * @author garen
 * @since 2021-01-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_email_sender")
@ApiModel(value = "SysEmailFromEntity对象", description = "邮件-发件人")
public class EmailSenderEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "代码")
    @TableField("`code`")
    private String code;

    @ApiModelProperty(value = "主机")
    @TableField("`host`")
    private String host;

    @ApiModelProperty(value = "端口")
    @TableField("`port`")
    private Integer port;

    @ApiModelProperty(value = "用户名")
    @TableField("`username`")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("`password`")
    private String password;

    @ApiModelProperty(value = "'协议'")
    @TableField("protocol")
    private String protocol;

    @ApiModelProperty(value = "回件人")
    @TableField("reply_to")
    private String replyTo;

    @ApiModelProperty(value = "描述")
    @TableField("`desc`")
    private String desc;

    @ApiModelProperty(value = "是否默认")
    @TableField("def_flag")
    private Boolean defFlag;

    @ApiModelProperty(value = "是否禁用")
    @TableField("dis_flag")
    private Boolean disFlag;

    @ApiModelProperty(value = "是否删除")
    @TableField("del_flag")
    @TableLogic
    private Boolean delFlag;

}