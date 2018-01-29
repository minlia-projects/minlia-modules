package com.minlia.module.captcha.constant;

/**
 * Created by garen on 2018/1/22.
 */
public class CaptchaApiCode {

    public static final Integer BASE_ON = 400000;

    public static final Integer CELLPHONE_NOT_FOUND_CODE = 400001;
    public static final String CELLPHONE_NOT_FOUND_DESC = "手机号码有误";

    public static final Integer CAPTCHA_EXPIRED_CODE = 400002;
    public static final String CAPTCHA_EXPIRED_DESC = "验证码已失效，请重新发送验证码";

    public static final Integer REPETITIOUS_FAILURE_CODE = 400003;
    public static final String REPETITIOUS_FAILURE_DESC = "验证码多次验证失败，请重新发送验证码";

    public static final Integer ALREADY_USED_CODE = 400004;
    public static final String ALREADY_USED_DESC = "验证码已使用，请重新发送";

    public static final Integer INVALID_CODE = 400005;
    public static final String INVALID_DESC = "验证码错误";

    public static final Integer TEMPLATE_NOT_FOUND_CODE = 400006;
    public static final String TEMPLATE_NOT_FOUND_DESC = "短信模板没找到, 请先配置";

    public static final Integer ONE_TIME_CODE = 400007;
    public static final String ONE_TIME_DESC = "一分钟只能发一次，请勿多次发送";

}
