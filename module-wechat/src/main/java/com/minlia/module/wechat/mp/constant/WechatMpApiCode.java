package com.minlia.module.wechat.mp.constant;


/**
 * Created by will on 6/21/17.
 * API响应码
 */
public class WechatMpApiCode {
    public WechatMpApiCode() {
        throw new AssertionError();
    }

    /**
     * 定义模块代码基数为900000
     */
    public static final Integer MODULE_CODE_BASED = 900000;


    public static final Integer ERROR_GET_ACCESS_TOKEN = 900002;

    public static final Integer ERROR_CREATE_WX_CODE = 900003;

    public static final Integer ERROR_UPLOAD = 900004;

}
