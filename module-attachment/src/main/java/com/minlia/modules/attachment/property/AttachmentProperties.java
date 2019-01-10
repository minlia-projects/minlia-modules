package com.minlia.modules.attachment.property;

import com.minlia.modules.attachment.enumeration.StorageTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "minlia.oss", ignoreUnknownFields = false)
@Data
public class AttachmentProperties {

    private StorageTypeEnum type = StorageTypeEnum.aliyun;

    private Map<String, String> local;

    public AttachmentProperties() {
        this.local = new HashMap();
    }

}
