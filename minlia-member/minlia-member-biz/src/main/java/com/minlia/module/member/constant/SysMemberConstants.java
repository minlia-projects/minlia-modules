package com.minlia.module.member.constant;


import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 */
public class SysMemberConstants {

    public final static String MODULE_NAME = "member";
    public final static String SMS_ENTERPRISE_CERTIFICATION_PASSED = "ENTERPRISE_CERTIFICATION_PASSED";
    public final static String SMS_ENTERPRISE_CERTIFICATION_FAIL = "ENTERPRISE_CERTIFICATION_FAIL";
    public final static String EMAIL_ENTERPRISE_CERTIFICATION_APPLICATION = "ENTERPRISE_CERTIFICATION_APPLICATION";

    public static class Authorize {
        public final static String READ = MODULE_NAME + SecurityConstant.OPERATION_READ_CODE;
        public final static String SELECT = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;
        public final static String REVIEW = MODULE_NAME + SecurityConstant.OPERATION_REVIEW_CODE;
        public final static String AUTH = MODULE_NAME + SecurityConstant.OPERATION_AUTHENTICATION_CODE;
        public final static String LATEST = MODULE_NAME + ".latest";
    }

    /**
     * 默认角色名称
     */
    public final static String DEFAULT_ROLE = "ROLE_MEMBER";

}