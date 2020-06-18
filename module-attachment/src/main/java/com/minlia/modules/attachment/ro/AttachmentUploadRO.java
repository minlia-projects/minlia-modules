package com.minlia.modules.attachment.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.InputStream;

/**
 * Created by garen on 2018/6/6.
 * Updated by will on 2020/06/08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentUploadRO {

    @ApiModelProperty(value ="存储桶名称" )
    private String bucketName;

    @ApiModelProperty(value ="文件路径" )
    private String key;

    @ApiModelProperty(value ="业务关系编号" )
    private String relationId;

    @ApiModelProperty(value ="业务归属" )
    private String belongsTo;

    @ApiModelProperty(value ="文件" )
    private File file;

    @ApiModelProperty(value ="输入流" )
    private InputStream inputStream;

}
