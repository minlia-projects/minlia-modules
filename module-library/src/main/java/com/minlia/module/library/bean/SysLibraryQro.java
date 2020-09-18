package com.minlia.module.library.bean;

import com.minlia.module.data.entity.BaseQueryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * <p>
 * 文库
 * </p>
 *
 * @author garen
 * @since 2020-09-11
 */
@Data
@ApiModel(value = "SysLibraryQro", description = "文库")
public class SysLibraryQro extends BaseQueryEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务类型")
    @Size(max = 20)
    private String type;

    @ApiModelProperty(value = "关键字")
    @Size(max = 50)
    private String keyword;

    @ApiModelProperty(value = "令牌")
    @Size(max = 32)
    private String accessKey;

    @ApiModelProperty(value = "文件名称")
    @Size(max = 50)
    private String fileName;

    @ApiModelProperty(value = "文件类型")
    @Size(max = 50)
    private String fileType;

    @ApiModelProperty(value = "地址")
    @Size(max = 255)
    private String url;

    @ApiModelProperty(value = "备注")
    @Size(max = 255)
    private String remark;

    @ApiModelProperty(value = "是否禁用")
    private Boolean disFlag;

}