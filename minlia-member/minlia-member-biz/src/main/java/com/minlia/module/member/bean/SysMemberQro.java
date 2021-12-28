package com.minlia.module.member.bean;

import com.minlia.module.data.bean.QueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@ApiModel(value = "SysMemberQro")
public class SysMemberQro extends QueryRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long uid;

    @ApiModelProperty(value = "姓名")
    @Size(max = 20)
    private String name;

    @ApiModelProperty(value = "身份证号码")
    @Size(max = 20)
    private String idNumber;

    @ApiModelProperty(value = "实名认证")
    private Boolean realName;

}