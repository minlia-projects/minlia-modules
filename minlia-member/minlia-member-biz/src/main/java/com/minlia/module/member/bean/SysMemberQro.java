package com.minlia.module.member.bean;

import com.minlia.module.data.bean.QueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysMemberQro")
public class SysMemberQro extends QueryRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "GUID")
    private Long uid;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机")
    private String cellphone;

    @ApiModelProperty(value = "是否认证")
    private boolean authFlag;

}