package com.minlia.module.email.ro;

import com.minlia.module.data.bean.QueryRequest;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("联行号-查询")
public class EmailRecordQRO extends QueryRequest {

    private Long id;

    private String createBy;

    private String lastModifiedBy;

    private LocalDateTime createDateTime;

    private LocalDateTime lastModifiedDate;

    private LocalDate createDate;

    private LocalDateTime startCreateDateTime;

    private LocalDateTime endCreateDateTime;

    /**
     * 应用ID
     */
    private String appid;

    /**
     * 编号
     */
    private String number;

    /**
     * 模版代码
     */
    private String templateCode;

    /**
     * 主送人
     */
    private String sendTo;

    /**
     * 抄送人
     */
    private String sendCc;

    /**
     * 秘送人
     */
    private String sendBcc;

    /**
     * 语言
     */
    private LocaleEnum locale;

    /**
     * 通道：阿里云、腾讯云、MINLIA
     */
    private String channel;

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

}