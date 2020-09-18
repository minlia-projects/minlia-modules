package com.minlia.module.rebecca.menu.constant;

import com.minlia.cloud.code.Code;
import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 * @date 2018/9/14
 */
public class MenuCode {

    public final static String MODULE_NAME = MinliaConstants.APP_NAME + ".menu";
    public final static String CREATE = MODULE_NAME + SecurityConstant.OPERATION_CREATE_CODE;
    public final static String UPDATE = MODULE_NAME + SecurityConstant.OPERATION_UPDATE_CODE;
    public final static String DELETE = MODULE_NAME + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String SEARCH = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;
    public final static String GRANT = MODULE_NAME + SecurityConstant.OPERATION_GRANT_CODE;
    public final static String DISPLAY = MODULE_NAME + SecurityConstant.OPERATION_DISPLAY_CODE;

    public enum Message implements Code {

        /**
         * 菜单不存在
         */
        NOT_EXISTS,

        /**
         * 菜单已存在
         */
        ALREADY_EXISTS,

        /**
         * 父级菜单不存在
         */
        PARENT_NOT_EXISTS,

        /**
         * 有子菜单不能删除
         */
        CAN_NOT_DELETE_HAS_CHILDREN;

        @Override
        public String module() {
            return MODULE_NAME;
        }
    }
}
