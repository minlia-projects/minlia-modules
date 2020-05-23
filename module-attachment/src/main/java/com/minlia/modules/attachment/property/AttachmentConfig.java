package com.minlia.modules.attachment.property;

import com.minlia.module.common.annotation.ConfigAutowired;
import com.minlia.modules.attachment.enumeration.OssChannelEnum;
import com.minlia.modules.attachment.enumeration.StorageTypeEnum;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigAutowired(type = "SYS_OSS_CONFIG")
public class AttachmentConfig {

    private OssChannelEnum channel = OssChannelEnum.ALIYUN;

}