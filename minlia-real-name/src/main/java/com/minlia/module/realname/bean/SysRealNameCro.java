package com.minlia.module.realname.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 * 实名认证
 * </p>
 *
 * @author garen
 * @since 2021-12-28
 */
@Data
@ApiModel(value = "SysRealNameCro", description = "实名认证")
public class SysRealNameCro {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "GUID")
    @JsonIgnore
    private Long uid;

    @ApiModelProperty(value = "姓名")
    @NotBlank
    @Size(max = 20)
    private String name;

    @ApiModelProperty(value = "身份证号码")
    @NotBlank
    @Size(max = 20)
    private String idNumber;

    @ApiModelProperty(value = "身份证正面照片")
    @Size(max = 255)
    private String frontPhoto;

    @ApiModelProperty(value = "身份证反面照片")
    @Size(max = 255)
    private String reversePhoto;

    @ApiModelProperty(value = "身份证手持照片")
    @Size(max = 255)
    private String holdPhoto;

}