package com.minlia.modules.rbac.constant;

import com.google.common.base.CaseFormat;
import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;
import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.module.common.constant.SymbolConstants;

import java.util.StringJoiner;

/**
 * Created by garen on 2018/9/14.
 */
public class UserCode {

    final static String CODE_PREFIX = MinliaConstants.APP_NAME + ".user";

    public enum Message implements Code{

        /**
         * 用户未注册
         */
        UNREGISTERED,

        /**
         * 用户不存在
         */
        NOT_EXISTS,

        /**
         * 用户已存在
         */
        ALREADY_EXISTS,

        /**
         * 推荐人不存在
         */
        REFERRAL_NOT_EXISTS,

        /**
         * 用户名已存在
         */
        USERNAME_ALREADY_EXISTS,

        /**
         * 手机号码已存在
         */
        CELLPHONE_ALREADY_EXISTS,

        /**
         * 邮箱已存在
         */
        EMAIL_ALREADY_EXISTS,

        /**
         * 暂不支持用户名注册
         */
        UNSUPPORTED_USERNAME_REGISTERED,

        /**
         * 暂不支持邮箱注册
         */
        UNSUPPORTED_EMAIL_REGISTERED,

        /**
         * 用户名不能为空
         */
        USERNAME_NOT_NULL,

        /**
         * 手机号码不能为空
         */
        CELLPHONE_NOT_NULL,

        /**
         * 邮箱不能为空
         */
        EMAIL_NOT_NULL,

        /**
         * 未设置邮箱
         */
        NOT_SET_EMAIL,

        /**
         * 未设置手机号码
         */
        NOT_SET_CELLPHONE,

        /**
         * 用户原密码错误
         */
        RAW_PASSWORD_ERROR,

        /**
         * 用户没有当前角色
         */
        DOES_NOT_HAD_ROLE;

        @Override
        public String code() {
            return this.name();
        }

        @Override
        public String i18nKey() {
            return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, new StringJoiner(SymbolConstants.DOT).add(CODE_PREFIX).add(this.getClass().getSimpleName()).add(this.name()).toString());
        }

        @Override
        public String message(){
            return Lang.get(this.i18nKey());
        }

    }

}
