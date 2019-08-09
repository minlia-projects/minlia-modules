package com.minlia.module.sms.ro;

import com.minlia.module.data.bean.QueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("联行号-查询")
public class SmsRecordQRO extends QueryRequest {

    private Long id;

    private String createBy;

    private String lastModifiedBy;

    private LocalDateTime createDate;

    private LocalDateTime lastModifiedDate;

    /**
     * 应用ID
     */
    private String appid;

    /**
     * 通道
     */
    private String channel;

    /**
     * 编码
     */
    private String code;

    /**
     * 接收人
     */
    private String sendTo;

    /**
     * 语言
     */
    private String locale;

    /**
     * 主题
     */
    private String subject;

    /**
     * 内容
     */
    private String content;

    /**
     * 备注
     */
    private String remark;

    /**
     * 发送成功
     */
    private Boolean successFlag;

    /**
     * 禁用标识
     */
    private Boolean disFlag;

}