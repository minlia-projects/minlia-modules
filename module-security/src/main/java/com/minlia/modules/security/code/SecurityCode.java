package com.minlia.modules.security.code;

import com.google.common.base.CaseFormat;
import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;
import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.module.common.constant.SymbolConstants;

import java.util.StringJoiner;

/**
 * Created by garen on 2018/9/14.
 */
public class SecurityCode {

    final static String CODE_PREFIX = MinliaConstants.APP_NAME + ".security";

    public enum Exception implements Code {

        /**
         * 不支持的请求方式
         */
        AUTH_METHOD_NOT_SUPPORTED,

        /**
         * 未登录
         */
        NOT_LOGIN,

        /**
         * 认证凭证不能为空：用户名、密码
         */
        AUTH_CREDENTIALS_NOT_FOUND,

        /**
         * 坏的登录凭证:用户名或密码错误
         */
        BAD_CREDENTIALS,

        /**
         * 未授权
         */
        NOT_AUTHORIZED,

        /**
         * 登录失败
         */
        LOGIN_FAILED,

        /**
         * 用户不存在
         */
        USERNAME_NOT_FOUND,

        /**
         * 账户已禁用
         */
        ACCOUNT_DISABLED,

        /**
         * 账户已锁定
         */
        ACCOUNT_LOCKED,

        /**
         * 账户已过期
         */
        ACCOUNT_EXPIRED,

        /**
         * 账户凭证已过期：密码已过期
         */
        ACCOUNT_CREDENTIALS_EXPIRED,

        /**
         * 访问令牌已过期:(登录态已过期)
         */
        JWT_TOKEN_EXPIRED,

        /**
         * 访问令牌无效:(登录态无效)
         */
        JWT_TOKEN_INVALID,

        /**
         * 访问令牌不能为空 Authorization header cannot be blank
         */
        JWT_TOKEN_NOT_NULL,

        /**
         * 坏的凭证：密码错误，已连续错误{0}次
         */
        AJAX_BAD_CREDENTIALS,

        /**
         * 锁定：账号已锁定，{0}秒后解锁
         */
        AJAX_LOCKED,

        /**
         * 认证服务异常
         */
        AUTH_SERVICE;

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
        public String message(String... args) {
            return Lang.get(this.i18nKey(), args);
        }

    }

    public enum Message implements Code {

        /**
         * 没有操作此记录的权限
         */
        NOT_DATA_AUTHORIZED;

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
        public String message(String... args) {
            return Lang.get(this.i18nKey(), args);
        }

    }

}
