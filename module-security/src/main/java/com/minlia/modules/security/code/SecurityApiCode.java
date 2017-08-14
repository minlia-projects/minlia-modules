package com.minlia.modules.security.code;

import com.minlia.cloud.code.ApiCode;

/**
 * Created by will on 6/17/17.
 * 安全相关API响应码
 */
public class SecurityApiCode extends ApiCode {
    public SecurityApiCode() {
        throw new AssertionError();
    }

    /**
     * 定义安全模块代码基数为11000
     */
    public static final Integer SECURITY_MODULE_CODE_BASED = BASED + 1000;

}
