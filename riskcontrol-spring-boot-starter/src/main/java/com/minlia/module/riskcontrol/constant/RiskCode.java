package com.minlia.module.riskcontrol.constant;

import com.google.common.base.CaseFormat;
import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;
import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.module.common.constant.SymbolConstants;

import java.util.StringJoiner;

/**
 * Created by garen on 2018/9/14.
 */
public class RiskCode {

    final static String CODE_PREFIX = MinliaConstants.APP_NAME + ".riskcontrol";

    public enum Message implements Code {

        /**
         * 黑名单，禁止访问
         */
        BLACK_IP,

        /**
         * 不允许的IP范围
         */
        BLACK_IP_SCOPE,

        /**
         * 同一个帐户{0}分钟内使用不同的IP登录系统,count={1}
         */
        NUM_DIFF_IP_LOGIN_MINS,

        /**
         * 近15分钟内同ip出现多次登陆失败,count=
         */
        NUM_SAME_IP_LOGIN_FAILURE_MINS;


        @Override
        public String code() {
            return this.name();
        }

        @Override
        public String i18nKey() {
            return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, new StringJoiner(SymbolConstants.DOT).add(CODE_PREFIX).add(this.getClass().getSimpleName()).add(this.name()).toString());
        }

        @Override
        public String message(Object... args) {
            return Lang.get(this.i18nKey(), args);
        }

    }

}
