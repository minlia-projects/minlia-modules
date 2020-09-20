package com.minlia.modules.security.constant;

import com.minlia.module.common.constant.MinliaConstants;

/**
 * @author will
 * @date 6/22/17
 */
public class SecurityConstant {

    public SecurityConstant() {
        throw new AssertionError();
    }

    final public static String MODULE_NAME = MinliaConstants.APP_NAME + ".security";

    public static final String SID = "sid";

    public static final String ROLE_SUPER_ADMIN_CODE = "ROLE_SUPER_ADMIN";
    public static final String ROLE_SUPER_ADMIN_DESC = "超级管理员";
    public static final Long ROLE_SUPER_ADMIN_ID = 1L;

    public static final String ROLE_SYSTEM_ADMIN_CODE = "ROLE_SYS_ADMIN";
    public static final String ROLE_SYSTEM_ADMIN_DESC = "系统管理员";
    public static final Long ROLE_SYSTEM_ADMIN_ID = 2L;

    public static final String ROLE_USER_CODE = "ROLE_USER";
    public static final String ROLE_USER_DESC = "用户";
    public static final Long ROLE_USER_ID = 2L;

    public static final String ROLE_GUEST_CODE = "ROLE_GUEST";
    public static final String ROLE_GUEST_DESC = "游客";
    public static final Long ROLE_GUEST_ID = 3L;

    public static final String REFRESH_TOKEN_PERMISSION = "REFRESH_TOKEN";

    public static final String OPERATION_MANAGE_CODE = ".manage";
    public static final String OPERATION_MANAGE_DESC_CN = "管理";
    public static final String OPERATION_MANAGE_DESC_EN = "Manage";

    public static final String OPERATION_CREATE_CODE = ".create";
    public static final String OPERATION_CREATE_DESC_CN = "创建";
    public static final String OPERATION_CREATE_DESC_EN = "Create";

    public static final String OPERATION_UPDATE_CODE = ".update";
    public static final String OPERATION_UPDATE_DESC_CN = "修改";
    public static final String OPERATION_UPDATE_DESC_EN = "Update";

    public static final String OPERATION_SAVE_CODE = ".save";
    public static final String OPERATION_SAVE_DESC_CN = "保存";
    public static final String OPERATION_SAVE_DESC_EN = "Save";

    public static final String OPERATION_DELETE_CODE = ".delete";
    public static final String OPERATION_DELETE_DESC_CN = "删除";
    public static final String OPERATION_DELETE_DESC_EN = "Delete";

    public static final String OPERATION_DISABLE_CODE = ".disable";
    public static final String OPERATION_DISABLE_DESC_CN = "禁用";
    public static final String OPERATION_DISABLE_DESC_EN = "Disable";

    public static final String OPERATION_READ_CODE = ".read";
    public static final String OPERATION_READ_DESC_CN = "读取";
    public static final String OPERATION_READ_DESC_EN = "Read";

    public static final String OPERATION_SEARCH_CODE = ".search";
    public static final String OPERATION_SEARCH_DESC_CN = "搜索";
    public static final String OPERATION_SEARCH_DESC_EN = "Search";

    public static final String OPERATION_RESET_CODE = ".reset";
    public static final String OPERATION_RESET_DESC_CN = "重置";
    public static final String OPERATION_RESET_DESC_EN = "Reset";

    public static final String OPERATION_CLEAR_CODE = ".clear";
    public static final String OPERATION_CLEAR_DESC_CN = "清除";
    public static final String OPERATION_CLEAR_DESC_EN = "Clear";

    public static final String OPERATION_REFRESH_CODE = ".refresh";
    public static final String OPERATION_REFRESH_DESC_CN = "刷新";
    public static final String OPERATION_REFRESH_DESC_EN = "Refresh";

    public static final String OPERATION_GRANT_CODE = ".grant";
    public static final String OPERATION_GRANT_DESC_CN = "授权";
    public static final String OPERATION_GRANT_DESC_EN = "Grant";

    public static final String OPERATION_IMPORT_CODE = ".import";
    public static final String OPERATION_IMPORT_DESC_CN = "导入";
    public static final String OPERATION_IMPORT_DESC_EN = "Import";

    public static final String OPERATION_EXPORT_CODE = ".export";
    public static final String OPERATION_EXPORT_DESC_CN = "导出";
    public static final String OPERATION_EXPORT_DESC_EN = "Export";

    public static final String OPERATION_DISPLAY_CODE = ".display";
    public static final String OPERATION_DISPLAY_DESC_CN = "显示";
    public static final String OPERATION_DISPLAY_DESC_EN = "display";

    public static final String OPERATION_APPLY_CODE = ".apply";
    public static final String OPERATION_APPLY_DESC_CN = "申请";
    public static final String OPERATION_APPLY_DESC_EN = "Approval";

    public static final String OPERATION_APPROVAL_CODE = ".approval";
    public static final String OPERATION_APPROVAL_DESC_CN = "审批";
    public static final String OPERATION_APPROVAL_DESC_EN = "Approval";

    public static final String OPERATION_PSSS_CODE = ".pass";
    public static final String OPERATION_PSSS_DESC_CN = "通过";
    public static final String OPERATION_PSSS_DESC_EN = "Pass";

    public static final String OPERATION_REJECT_CODE = ".reject";
    public static final String OPERATION_REJECT_DESC_CN = "驳回";
    public static final String OPERATION_REJECT_DESC_EN = "Reject";

    public static final String OPERATION_CANCEL_CODE = ".cancel";
    public static final String OPERATION_CANCEL_DESC_CN = "取消";
    public static final String OPERATION_CANCEL_DESC_EN = "Cancel";

    public static final String OPERATION_REVIEW_CODE = "review";
    public static final String OPERATION_REVIEW_DESC_CN = "审核";
    public static final String OPERATION_REVIEW_DESC_EN = "Review";

    public static final String OPERATION_AUTHENTICATION_CODE = "authentication";
    public static final String OPERATION_AUTHENTICATION_DESC_CN = "认证";
    public static final String OPERATION_AUTHENTICATION_DESC_EN = "Authentication";

}
