package com.minlia.module.attachment.v1.body;


import com.minlia.cloud.query.body.QueryRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "附件-查询")
@Data
public class AttachmentQueryRequestBody implements QueryRequestBody {

    @ApiModelProperty(value = "业务类型编码", example = "account.identity.frontend")
    private String businessType;

    @ApiModelProperty(value = "业务ID", example = "123")
    private String businessId;

    @ApiModelProperty(value = "附件类型",example = "jpg")
    private String type;

    @ApiModelProperty(value = "访问令牌", example = "234ehdskjds-sdfas-dfds-fds-fa-sdfs-sdfas-d")
    private String accessToken;

}
