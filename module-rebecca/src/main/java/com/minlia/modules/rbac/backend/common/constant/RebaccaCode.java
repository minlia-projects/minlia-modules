package com.minlia.modules.rbac.backend.common.constant;

import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;

/**
 * Created by garen on 2018/9/14.
 */
public class RebaccaCode {

    public enum Exception implements Code {

        TEST(-1,"TEST");

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
         * 用户不存在
         */
        USER_NOT_EXISTED(100200,"system.user.message.100200"),

        /**
         * 用户已存在
         */
        USER_ALREADY_EXISTS(100201,"system.user.message.100201"),

        /**
         * 推荐人不存在
         */
        USER_REFERRAL_NOT_EXISTED(100202,"system.user.message.100202"),

        /**
         * 暂不支持用户名注册
         */
        USER_UNSUPPORTED_USERNAME_REGISTERED(100203,"system.user.message.100203"),

        /**
         * 暂不支持邮箱注册
         */
        USER_UNSUPPORTED_EMAIL_REGISTERED(100204,"system.user.message.100204"),

        /**
         * 用户名已存在
         */
        USERNAME_ALREADY_EXISTED(100205,"system.user.message.100205"),

        /**
         * 手机号码已存在
         */
        USER_EMAIL_ALREADY_EXISTED(100206,"system.user.message.100206"),

        /**
         * 邮箱已存在
         */
        USER_CELLPHONE_ALREADY_EXISTED(100207,"system.user.message.100207"),

        /**
         * 用户原密码错误
         */
        USER_RAW_PASSWORD_ERROR(100208,"system.user.message.100208"),

        /**
         * 未绑定手机号码
         */
        USER_NO_CELLPHONE(100209,"system.user.message.100209"),



        /**
         * 角色不存在
         */
        ROLE_NOT_EXISTED(100300,"system.role.message.100300"),

        /**
         * 角色已存在
         */
        ROLE_ALREADY_EXISTED(100301,"system.role.message.100301"),


        /**
         * 权限点已存在
         */
        PERMISSION_ALREADY_EXISTED(100400,"system.permission.message.100400"),


        /**
         * 导航不存在
         */
        NAVIGATION_NOT_EXISTS(100500,"system.navigation.message.100500"),

        /**
         * 导航已存在
         */
        NAVIGATION_ALREADY_EXISTS(100501,"system.navigation.message.100501"),

        /**
         * 导航父级不存在
         */
        NAVIGATION_PARENT_NOT_EXISTS(100502,"system.navigation.message.100502"),

        /**
         * 有子导航不能删除
         */
        NAVIGATION_CAN_NOT_DELETE_HAS_CHILDREN(100503,"system.navigation.message.100503");

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
