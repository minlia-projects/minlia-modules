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
public class RebaccaCode {

    final static String CODE_PREFIX = MinliaConstants.APP_NAME + ".rebacca";

    public enum Message implements Code{

        UNREGISTERED,

        /**
         * 用户不存在
         */
        USER_NOT_EXISTED,

        /**
         * 用户已存在
         */
        USER_ALREADY_EXISTS,

        /**
         * 推荐人不存在
         */
        USER_REFERRAL_NOT_EXISTED,

        /**
         * 暂不支持用户名注册
         */
        USER_UNSUPPORTED_USERNAME_REGISTERED,

        /**
         * 暂不支持邮箱注册
         */
        USER_UNSUPPORTED_EMAIL_REGISTERED,

        /**
         * 用户名已存在
         */
        USERNAME_ALREADY_EXISTED,

        /**
         * 手机号码已存在
         */
        USER_CELLPHONE_ALREADY_EXISTED,

        /**
         * 邮箱已存在
         */
        USER_EMAIL_ALREADY_EXISTED,

        /**
         * 用户原密码错误
         */
        USER_RAW_PASSWORD_ERROR,

        /**
         * 未绑定手机号码
         */
        USER_NO_CELLPHONE,

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
         * 未绑定邮箱
         */
        USER_NO_EMAIL,

        /**
         * 用户没有当前角色
         */
        USER_DOES_NOT_HAD_ROLE,



        /**
         * 角色不存在
         */
        ROLE_NOT_EXISTED,

        /**
         * 角色已存在
         */
        ROLE_ALREADY_EXISTED,


        /**
         * 权限点已存在
         */
        PERMISSION_ALREADY_EXISTED,


        /**
         * 导航不存在
         */
        NAVIGATION_NOT_EXISTS,

        /**
         * 导航已存在
         */
        NAVIGATION_ALREADY_EXISTS,

        /**
         * 导航父级不存在
         */
        NAVIGATION_PARENT_NOT_EXISTS,

        /**
         * 有子导航不能删除
         */
        NAVIGATION_CAN_NOT_DELETE_HAS_CHILDREN;

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
