package com.minlia.modules.security.code;

import com.minlia.cloud.annotation.i18n.Localize;
import com.minlia.cloud.annotation.i18n.Localized;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.constant.Constants.LanguageTypeEnum;

/**
 * Created by will on 6/17/17.
 * 安全相关API响应码
 */
@Localized
public class SecurityApiCode extends ApiCode {

    /**
     * 定义安全模块代码基数为11000
     */
    public static final int SECURITY_MODULE_CODE_BASED = BASED_ON + 1000;

    @Localized(values={
            @Localize(type= LanguageTypeEnum.ExceptionsApiCode,locale = "en_US",message = "No account bound to system user, please check that login with system admin, without bound to any account?"),
            @Localize(type= LanguageTypeEnum.ExceptionsApiCode,locale = "zh_CN",message = "当前登录的用户未绑定系统账号, 是否使用了开发时自带的admin来测试功能?"),
    })
    public static final int ACCOUNT_NOT_BOUND_TO_USER=SECURITY_MODULE_CODE_BASED + 1;
  @Localized(values={
      @Localize(type= LanguageTypeEnum.ExceptionsApiCode,locale = "en_US",message = "No role found"),
      @Localize(type= LanguageTypeEnum.ExceptionsApiCode,locale = "zh_CN",message = "系统无法找到指定的角色"),
  })
    public static final int ROLE_NOT_FOUND =SECURITY_MODULE_CODE_BASED + 2;

    public SecurityApiCode() {
        throw new AssertionError();
    }


}
