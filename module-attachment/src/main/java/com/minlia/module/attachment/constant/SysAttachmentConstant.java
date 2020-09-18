package com.minlia.module.attachment.constant;

import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 * <p>
 * 常量
 */
public class SysAttachmentConstant {

    public final static String MODULE_NAME = MinliaConstants.APP_NAME + ".attachment";
    public final static String CREATE = MODULE_NAME + SecurityConstant.OPERATION_CREATE_CODE;
    public final static String UPDATE = MODULE_NAME + SecurityConstant.OPERATION_UPDATE_CODE;
    public final static String DELETE = MODULE_NAME + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String SEARCH = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;
    public final static String UPLOAD = MODULE_NAME + ".upload";

    /**
     * 默认的 OSS Endpoint
     */
    public static final String DEFAULT_OSS_ENDPOINT = "http://storage.aliyun.com";

    /**
     * 如何获取API 密钥的帮助信息
     */
    public static final String API_KEY_HELP = "如何获取API密钥（Access ID & Access Key ）请参考http://help.aliyun.com/manual?helpId=786";

    /**
     * BUCKET帮助信息
     */
    public static final String BUCKET_HELP = "Bucket知识请参考help.aliyun.com/manual?helpId=239";

    /**
     * 访问控制帮助信息
     */
    public static final String ACL_HELP = "访问控制知识请参考http://help.aliyun.com/manual?lastSortId=264";

    /**
     * 成功
     */
    public static final boolean SUCCESS = true;

    /**
     * 失败
     */
    public static final boolean FAILURE = false;

    /**
     * 点
     */
    public static final String DOT = ".";

    /**
     * 斜线
     */
    public static final String SLASH = "/";

    /**
     * 空字符串
     */
    public static final String EMPTY = "";

    public static final String DEFAULT_PATH = "others/";

    public static final String OLD_RETURN_URL_PATTERN = "//%s.%s/%s";

    public static final String RETURN_URL_PATTERN = "//%s/%s";

    public enum Image {

        /**
         * 头像
         */
        IMAGE_HEAD,

        /**
         * 封面
         */
        IMAGE_COVER,

        /**
         * 横幅
         */
        IMAGE_BANNER,

        /**
         * 图标
         */
        IMAGE_ICON,

        /**
         * 截图
         */
        IMAGE_SCREENSHOT
    }

    public enum Video {
        /**
         * 视屏
         */
        VIDEO
    }

}