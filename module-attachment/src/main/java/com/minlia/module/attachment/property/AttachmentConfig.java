package com.minlia.module.attachment.property;

import com.minlia.module.attachment.enums.OssChannelEnum;
import com.minlia.module.common.annotation.ConfigAutowired;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigAutowired(type = "SYS_OSS_CONFIG")
public class AttachmentConfig {

    private OssChannelEnum channel = OssChannelEnum.ALIYUN;

}