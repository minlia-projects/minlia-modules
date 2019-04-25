package com.minlia.modules.attachment.ro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.InputStream;

/**
 * Created by garen on 2018/6/6.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentUploadRO {

    /**
     * 存储桶名称
     */
    private String bucketName;

    /**
     * 文件路径
     */
    private String key;

    private String relationId;

    private String belongsTo;

    private File file;

    private InputStream inputStream;

}
