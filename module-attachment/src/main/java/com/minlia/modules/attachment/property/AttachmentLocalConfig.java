package com.minlia.modules.attachment.property;

import com.minlia.module.common.annotation.ConfigAutowired;
import com.minlia.modules.attachment.enumeration.StorageTypeEnum;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigAutowired(type = "SYS_OSS_LOCAL_CONFIG")
public class AttachmentLocalConfig {

    private String key;

    private String secret;

    private String bucket;

    private String region;

}