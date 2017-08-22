package com.minlia.modules.security.code;

import com.minlia.cloud.annotation.i18n.Localize;
import com.minlia.cloud.annotation.i18n.Localized;
import com.minlia.cloud.code.ApiCode;

/**
 * Created by will on 6/17/17.
 * 安全相关API响应码
 */
public class SecurityApiCode extends ApiCode {


    /**
     * 定义安全模块代码基数为11000
     */
    public static final int SECURITY_MODULE_CODE_BASED = BASED_ON + 1000;


    @Localized(values={
            @Localize(locale = "en_US",message = "当前登录的用户未绑定系统账号, 是否使用了开发时自带的admin来测试功能?"),
    })
    public static final int ACCOUNT_NOT_BOUND_TO_USER=SECURITY_MODULE_CODE_BASED + 1;

    public SecurityApiCode() {
        throw new AssertionError();
    }


}
