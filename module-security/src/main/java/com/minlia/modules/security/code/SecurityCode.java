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
        AUTH_METHOD_NOT_SUPPORTED(100100,"system.security.exception.100100"),

        /**
         * 未登录
         */
        NOT_LOGIN(100101,"system.security.exception.100101"),

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
         * 登录失败
         */
        LOGIN_FAILED(100105,"system.security.exception.100105"),

        /**
         * 用户不存在
         */
        USERNAME_NOT_FOUND(100106,"system.security.exception.100106"),

        /**
         * 账户已禁用
         */
        ACCOUNT_DISABLED(100107,"system.security.exception.100107"),

        /**
         * 账户已锁定
         */
        ACCOUNT_LOCKED(100108,"system.security.exception.100108"),

        /**
         * 账户已过期
         */
        ACCOUNT_EXPIRED(100109,"system.security.exception.100109"),

        /**
         * 账户凭证已过期：密码已过期
         */
        ACCOUNT_CREDENTIALS_EXPIRED(100110,"system.security.exception.100110"),

        /**
         * 访问令牌已过期:(登录态已过期)
         */
        JWT_TOKEN_EXPIRED(100111,"system.security.exception.100111"),

        /**
         * 访问令牌无效:(登录态无效)
         */
        JWT_TOKEN_INVALID(100112,"system.security.exception.100112"),

        /**
         * 访问令牌不能为空 Authorization header cannot be blank
         */
        JWT_TOKEN_NOT_NULL(100113,"system.security.exception.100113"),

        /**
         * 访问令牌有误 Invalid authorization header size.
         */
        JWT_BAD_TOKEN(100114,"system.security.exception.100114"),

        /**
         * 坏的凭证：密码错误，已连续错误{0}次
         */
        AJAX_BAD_CREDENTIALS(100115,"system.security.exception.100115"),

        /**
         * 锁定：账号已锁定，{0}秒后解锁
         */
        AJAX_LOCKED(100116,"system.security.exception.100116"),

        /**
         * 认证服务异常
         */
        AUTH_SERVICE(100117,"system.security.exception.100117");

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

        /**
         * 没有操作此记录的权限
         */
        NOT_DATA_AUTHORIZED(100100, "没有操作此记录的权限");

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
