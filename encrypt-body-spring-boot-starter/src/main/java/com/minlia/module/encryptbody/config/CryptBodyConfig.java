package com.minlia.module.encryptbody.config;

import com.minlia.module.common.annotation.ConfigAutowired;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author garen
 * @version 1.0
 * @description 加密配置
 * @date 2019/8/22 5:39 PM
 */
@Data
@Component
@ConfigAutowired(type = "SYS_CRYPT_BODY_CONFIG")
public class CryptBodyConfig {

    /**
     * 开关
     */
    private Boolean switchFlag = true;

}
