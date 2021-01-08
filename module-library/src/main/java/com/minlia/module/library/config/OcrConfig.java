package com.minlia.module.library.config;

import com.minlia.module.common.annotation.ConfigAutowired;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigAutowired(type = "SYS_OCR_CONFIG")
public class OcrConfig {

//    private OssChannelEnum channel = OssChannelEnum.ALIYUN;

    private String filePath;

}