package com.minlia.module.sms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 短信历史
 * </p>
 *
 * @author garen
 * @since 2020-12-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_sms_record")
@ApiModel(value = "SmsRecordEntity对象", description = "短信历史")
public class SmsRecordEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private String tenantId;

    @ApiModelProperty(value = "通道：阿里云、腾讯云")
    @TableField("channel")
    private String channel;

    @ApiModelProperty(value = "模版代码")
    @TableField("template_code")
    private String templateCode;

    @ApiModelProperty(value = "接收人")
    @TableField("send_to")
    private String sendTo;

    @ApiModelProperty(value = "语言")
    @TableField("locale")
    private String locale;

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
