package com.minlia.module.language.v1.code;


import com.minlia.cloud.annotation.i18n.Localized;
import com.minlia.cloud.code.ApiCode;

/**
 * Created by will on 6/17/17.
 * API响应码
 */
@Localized
public class LanguageApiCode extends ApiCode {
    public LanguageApiCode() {
        throw new AssertionError();
    }

    /**
     * 定义模块代码基数为12000
     */
    public static final Integer MODULE_CODE_BASEDON = BASED_ON + 2000;

}
