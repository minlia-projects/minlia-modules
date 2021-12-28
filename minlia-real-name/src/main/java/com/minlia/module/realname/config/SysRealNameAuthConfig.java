package com.minlia.module.realname.config;

import com.minlia.module.common.annotation.ConfigAutowired;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 实名认证配置参数
 *
 * @author garen
 */
@Component
@ConfigAutowired(type = "SYS_REAL_NAME")
@Data
public class SysRealNameAuthConfig {

    private String url;

    private String appKey;

    private String appSecret;

    private String appCode;

/*
    INSERT INTO `sys_bible_item` VALUES (1304260314413568091, 'SYS_REAL_AUTH', 'URL', 'http://checkone.market.alicloudapi.com/chinadatapay/1882', '请求路径', 2, NULL, NULL, NULL, 1303549691392389119, '2020-09-11 11:27:45', NULL, '2021-12-17 14:54:49');
    INSERT INTO `sys_bible_item` VALUES (1304261691302907992, 'SYS_REAL_AUTH', 'APP_KEY', '204022245', 'APP_KEY', 3, NULL, NULL, NULL, 1303549691392389119, '2020-09-11 11:33:13', NULL, '2020-10-03 20:16:32');
    INSERT INTO `sys_bible_item` VALUES (1304261922681688093, 'SYS_REAL_AUTH', 'APP_SECRET', 'HSj7MxCxRDKUIZ5iYlGeGUP9cyKuPTKL', 'APP_SECRET', 4, NULL, NULL, NULL, 1303549691392389119, '2020-09-11 11:34:09', NULL, '2021-03-19 19:04:12');
    INSERT INTO `sys_bible_item` VALUES (1304262238558916694, 'SYS_REAL_AUTH', 'APP_CODE', 'f238ec1ca5e94358833c36955d64f3fc', 'APP_CODE', 5, NULL, NULL, NULL, 1303549691392389119, '2020-09-11 11:35:24', NULL, '2021-01-25 18:23:16');
*/
}