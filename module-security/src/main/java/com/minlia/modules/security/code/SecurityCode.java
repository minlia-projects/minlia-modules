package com.minlia.modules.security.code;

import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;

/**
 * Created by garen on 2018/9/14.
 */
public class SecurityCode {

    public enum Exception implements Code {

        /**
         * 不支持的请求方式
         */
        AUTH_METHOD_NOT_SUPPORTED(100111,"system.security.exception.100111"),

        /**
         * 未登录
         */
        NOT_LOGIN(100100,"system.security.exception.100100"),

        /**
         * 登录失败
         */
        LOGIN_FAILED(100101,"system.security.exception.100101"),

        /**
         * 认证凭证不能为空：用户名、密码
         */
        AUTH_CREDENTIALS_NOT_FOUND(100102,"system.security.exception.100102"),

        /**
         * 坏的登录凭证:用户名或密码错误
         */
        BAD_CREDENTIALS(100103,"system.security.exception.100103"),

        /**
         * 未授权
         */
        NOT_AUTHORIZED(100104,"system.security.exception.100104"),

        /**
         * 用户不存在
         */
        USERNAME_NOT_FOUND(100110,"system.security.exception.100110"),

        /**
         * 账户不可用
         */
        ACCOUNT_DISABLED(100105,"system.security.exception.100105"),

        /**
         * 账户已锁定
         */
        ACCOUNT_LOCKED(100106,"system.security.exception.100106"),

        /**
         * 账户已过期
         */
        ACCOUNT_EXPIRED(100106,"system.security.exception.100106"),

        /**
         * 账户凭证已过期：密码已过期
         */
        ACCOUNT_CREDENTIALS_EXPIRED(100106,"system.security.exception.100106"),

        /**
         * 访问令牌已过期
         */
        JWT_TOKEN_EXPIRED(100107,"system.security.exception.100107"),

        /**
         * 访问令牌无效
         */
        JWT_TOKEN_INVALID(100108,"system.security.exception.100108"),

        /**
         * 访问令牌 Authorization header cannot be blank
         */
        JWT_ACCEPTABLE_NOT_NULL(100108,"system.security.exception.100108"),

        /**
         * 访问令牌 Invalid authorization header size.
         */
        JWT_ACCEPTABLE_INVALID_SIZE(100108,"system.security.exception.100108"),

        /**
         * 坏的凭证：密码错误，已连续错误%s次
         */
        AJAX_BAD_CREDENTIALS(100108,"system.security.exception.100108"),

        /**
         * 锁定：账号已锁定，%s秒后解锁
         */
        AJAX_LOCKED(100108,"system.security.exception.100108"),

        /**
         * 认证服务异常
         */
        AUTH_SERVICE(100111,"system.security.exception.100111");

        private int code;
        private String i18nKey;

        Exception(int code, String i18nKey) {
            this.code = code;
            this.i18nKey = i18nKey;
        }

        @Override
        public int code() {
            return code;
        }

        @Override
        public String i18nKey() {
            return i18nKey;
        }

        @Override
        public String message(){
            return Lang.get(this.i18nKey);
        }

    }

    public enum Message implements Code{

        M(1,"");

        private int code;
        private String i18nKey;

        Message(int code, String i18nKey) {
            this.code = code;
            this.i18nKey = i18nKey;
        }

        @Override
        public int code() {
            return code;
        }

        @Override
        public String i18nKey() {
            return i18nKey;
        }

        @Override
        public String message(){
            return Lang.get(this.i18nKey);
        }

    }

}
