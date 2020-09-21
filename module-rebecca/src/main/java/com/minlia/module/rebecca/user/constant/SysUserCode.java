package com.minlia.module.rebecca.user.constant;

import com.minlia.cloud.code.Code;
import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 * @date 2018/9/14
 */
public class SysUserCode {

    public final static String MODULE_NAME = MinliaConstants.APP_NAME + ".user";
    public final static String CREATE = MODULE_NAME + SecurityConstant.OPERATION_CREATE_CODE;
    public final static String UPDATE = MODULE_NAME + SecurityConstant.OPERATION_UPDATE_CODE;
    public final static String DELETE = MODULE_NAME + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String SEARCH = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;
    public final static String GRANT = MODULE_NAME + SecurityConstant.OPERATION_GRANT_CODE;
    public final static String READ = MODULE_NAME + SecurityConstant.OPERATION_READ_CODE;

    public enum Message implements Code {

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
         * 已禁用
         */
        ALREADY_DISABLED,

        /**
         * 已注销
         */
        ALREADY_TERMINATED,

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
        DOES_NOT_HAD_ROLE,

        /**
         * 过去6个月内更改手机号码地址的次数超过5次
         */
        CHANGE_CELLPHONE_OVER_TIMES,

        /**
         * 过去6个月内更改电子邮件地址的次数超过5次
         */
        CHANGE_EMAIL_OVER_TIMES,

        /**
         * 新密码不能和旧密码一致
         */
        NEW_PASSWORD_EQUALS_OLD;


        @Override
<<<<<<< HEAD:module-rebecca/src/main/java/com/minlia/module/rebecca/user/constant/SysUserCode.java
        public String module() {
            return MODULE_NAME;
=======
        public String message(Object... var1){
            return Lang.get(this.i18nKey(), var1);
>>>>>>> dev/garen:module-rebecca/src/main/java/com/minlia/modules/rbac/constant/UserCode.java
        }

    }

}