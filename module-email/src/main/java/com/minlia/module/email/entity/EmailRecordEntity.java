package com.minlia.module.email.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 邮件记录
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
@TableName("sys_email_record")
@ApiModel(value = "SysEmailRecordEntity对象", description = "邮件记录")
public class EmailRecordEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用ID")
    @TableField("appid")
    private String appid;

    @ApiModelProperty(value = "通道：阿里云、腾讯云、MINLIA")
    @TableField("channel")
    private String channel;

    @ApiModelProperty(value = "模版代码")
    @TableField("template_code")
    private String templateCode;

    @ApiModelProperty(value = "主送人")
    @TableField("send_to")
    private String sendTo;

    @ApiModelProperty(value = "抄送人")
    @TableField("send_cc")
    private String sendCc;

    @ApiModelProperty(value = "秘送人")
    @TableField("send_bcc")
    private String sendBcc;

    @ApiModelProperty(value = "语言")
    @TableField("locale")
    private LocaleEnum locale;

    @ApiModelProperty(value = "主题")
    @TableField("subject")
    private String subject;

    @ApiModelProperty(value = "内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "发送成功")
    @TableField("success_flag")
    private Boolean successFlag;


}
