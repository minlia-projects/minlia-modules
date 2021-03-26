package com.minlia.modules.security.code;

import com.minlia.cloud.code.Code;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 * @date 2018/9/14
 */
public class SecurityCode {

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
         * 身份认证服务未找到
         */
        PROVIDER_AUTHENTICATION_SERVICE_NOT_FOUND,

        /**
         * 认证服务异常
         */
        AUTH_SERVICE,

        /**
         * 已在其它地点登陆
         */
        LOGGED_AT_ANOTHER_LOCATION;

        @Override
        public String module() {
            return SecurityConstant.MODULE_NAME;
        }

    }

    public enum Message implements Code {

        /**
         * 没有操作此记录的权限
         */
        NOT_DATA_AUTHORIZED,

        NO_CURRENT_ROLE_PERMISSIONS;

        @Override
        public String module() {
            return SecurityConstant.MODULE_NAME;
        }

    }

}
