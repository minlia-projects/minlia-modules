package com.minlia.module.captcha.constant;

import com.google.common.base.CaseFormat;
import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;
import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.module.common.constant.SymbolConstants;

import java.util.StringJoiner;

/**
 * Created by garen on 2018/1/22.
 */
public class CaptchaCode {

    final static String CODE_PREFIX = MinliaConstants.APP_NAME + ".captcha";

    public enum Message implements Code{

        /**
         * 手机号码格式有误:请输入11位有效手机号码
         */
        CELLPHONE_WRONG_FORMAT,

        /**
         * 验证码已失效，请重新发送验证码
         */
        CAPTCHA_EXPIRED,

        /**
         * 验证码错误
         */
        CAPTCHA_ERROR,

        /**
         * 验证码多次验证失败，请重新发送验证码
         */
        CAPTCHA_REPETITIOUS_ERROR,

        /**
         * 验证码已使用，请重新发送
         */
        ALREADY_USED,

        /**
         * 验证码已禁用，30分钟后解锁
         */
        ALREADY_LOCKED,

        /**
         * 短信模板没找到, 请先配置
         */
        TEMPLATE_NOT_FOUND,

        /**
         * 短信模板没找到, 请先配置
         */
        SEND_ONE_TIME,

        /**
         * 一分钟只能发送一次，请勿多次发送
         */
        ONCE_PER_MINUTE,

        /**
         * 验证码发送失败
         */
        SEND_FAILURE,

        /**
         * 验证码找不到
         */
        NOT_FOUND,

        /**
         * 邮箱格式有误:请输入正确的邮箱地址
         */
        EMAIL_WRONG_FORMAT,

        /**
         * 手机号码不能为空
         */
        CELLPHONE_NOT_NULL,

        /**
         * 邮箱不能为空
         */
        EMAIL_NOT_NULL;

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
