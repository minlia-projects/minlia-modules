package com.minlia.module.captcha.constant;

import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;

/**
 * Created by garen on 2018/1/22.
 */
public class CaptchaCode {

    public enum Message implements Code{

        /**
         * 手机号码格式有误:请输入11位有效手机号码
         */
        CELLPHONE_WRONG_FORMAT(101400,"system.captcha.message.101400"),

        /**
         * 验证码已失效，请重新发送验证码
         */
        CAPTCHA_EXPIRED(101401,"system.captcha.message.101401"),

        /**
         * 验证码错误
         */
        CAPTCHA_ERROR(101402,"system.captcha.message.101402"),

        /**
         * 验证码多次验证失败，请重新发送验证码
         */
        CAPTCHA_REPETITIOUS_ERROR(101403,"system.captcha.message.101403"),

        /**
         * 验证码已使用，请重新发送
         */
        ALREADY_USED(101404,"system.captcha.message.101404"),

        /**
         * 短信模板没找到, 请先配置
         */
        TEMPLATE_NOT_FOUND(101405,"system.captcha.message.101405"),

        /**
         * 短信模板没找到, 请先配置
         */
        SEND_ONE_TIME(101406,"system.captcha.message.101406"),

        /**
         * 一分钟只能发送一次，请勿多次发送
         */
        ONCE_PER_MINUTE(101407,"system.captcha.message.101407"),

        /**
         * 验证码发送失败
         */
        SEND_FAILURE(101408,"system.captcha.message.101408"),

        /**
         * 验证码找不到
         */
        NOT_FOUND(101409,"system.captcha.message.101409");

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
