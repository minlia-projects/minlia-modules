package com.minlia.modules.attachment.bean;


import com.minlia.module.data.body.QueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "附件-查询")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentQO implements QueryRequest {

    @ApiModelProperty(value = "ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "业务类型编码", example = "account.identity.frontend")
    private String belongsTo;

    @ApiModelProperty(value = "业务ID", example = "123")
    private String relationId;

    @ApiModelProperty(value = "附件类型",example = "jpg")
    private String type;

    @ApiModelProperty(value = "访问令牌", example = "234ehdskjds-sdfas-dfds-fds-fa-sdfs-sdfas-d")
    private String accessKey;

}
