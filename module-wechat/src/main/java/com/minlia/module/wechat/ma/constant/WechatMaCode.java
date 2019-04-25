package com.minlia.module.wechat.ma.constant;


import com.google.common.base.CaseFormat;
import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;
import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.module.common.constant.SymbolConstants;

import java.util.StringJoiner;

/**
 * Created by will on 6/21/17.
 * API响应码
 */
public class WechatMaCode {

    final static String CODE_PREFIX = MinliaConstants.APP_NAME + ".wxma";

    public enum Message implements Code {

        /**
         * 小程序路径%s不能为空
         */
        MA_PATH_NOT_NULL,

        /**
         * 小程序参数{type}未配置
         */
        PARAMETER_NOT_CONFIG;

        @Override
        public String code() {
            return this.name();
        }

        @Override
        public String i18nKey() {
            return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, new StringJoiner(SymbolConstants.DOT)
                    .add(CODE_PREFIX)
                    .add(this.getClass().getSimpleName())
                    .add(this.name()).toString());
        }

        @Override
        public String message(){
            return Lang.get(this.i18nKey());
        }

    }

}
