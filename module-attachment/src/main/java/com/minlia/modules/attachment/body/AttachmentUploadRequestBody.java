package com.minlia.modules.attachment.body;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by garen on 2018/6/6.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentUploadRequestBody {

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

    private FileInputStream fileInputStream;

}
