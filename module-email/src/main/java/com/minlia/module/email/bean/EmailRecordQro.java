package com.minlia.module.email.bean;

import com.minlia.module.data.entity.BaseQueryEntity;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author garen
 */
@Data
@ApiModel
public class EmailRecordQro extends BaseQueryEntity {

    @ApiModelProperty(value = "应用ID")
    private String appid;

    @ApiModelProperty(value = "通道：阿里云、腾讯云、MINLIA")
    private String channel;

    @ApiModelProperty(value = "模版代码")
    private String templateCode;

    @ApiModelProperty(value = "主送人")
    private String sendTo;

    @ApiModelProperty(value = "抄送人")
    private String sendCc;

    @ApiModelProperty(value = "秘送人")
    private String sendBcc;

    @ApiModelProperty(value = "语言")
    private LocaleEnum locale;

    @ApiModelProperty(value = "主题")
    private String subject;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "发送成功")
    private Boolean successFlag;

}