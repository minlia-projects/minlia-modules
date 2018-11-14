package com.minlia.modules.attachment.property;

import com.minlia.modules.attachment.enumeration.AttachmentUploadTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minlia.oss", ignoreUnknownFields = false)
@Data
public class AttachmentProperties {

    private AttachmentUploadTypeEnum type = AttachmentUploadTypeEnum.aliyun;

}
