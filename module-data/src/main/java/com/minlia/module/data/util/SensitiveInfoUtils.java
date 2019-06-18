package com.minlia.module.data.util;

import com.minlia.module.common.constant.SymbolConstants;
import org.apache.commons.lang3.StringUtils;

public class SensitiveInfoUtils {

    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     */
    public static String chineseName(final String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        final String name = StringUtils.left(fullName, 1);
        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
    }

    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     */
    public static String chineseName(final String familyName, final String givenName) {
        if (StringUtils.isBlank(familyName) || StringUtils.isBlank(givenName)) {
            return "";
        }
        return chineseName(familyName + givenName);
    }

    /**
     * [身份证号] 显示最后四位，其他隐藏。共计18位或者15位。<例子：*************5762>
     */
    public static String idCardNum(final String id) {
        if (StringUtils.isBlank(id)) {
            return "";
        }
        return StringUtils.left(id, 3).concat(StringUtils
                .removeStart(StringUtils.leftPad(StringUtils.right(id, 3), StringUtils.length(id), "*"),
                        "***"));
    }

    /**
     * [固定电话] 后四位，其他隐藏<例子：****1234>
     */
    public static String fixedPhone(final String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*");
    }

    /**
     * [手机号码] 前三位，后四位，其他隐藏<例子:138******1234>
     */
    public static String mobilePhone(final String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return aroundPad(num, 3, 4, SymbolConstants.STAR);
//        return StringUtils.left(num, 3).concat(StringUtils
//                .removeStart(StringUtils.leftPad(StringUtils.right(num, 3), StringUtils.length(num), "*"),
//                        "***"));
    }

    /**
     * [地址] 只显示到地区，不显示详细地址；我们要对个人信息增强保护<例子：北京市海淀区****>
     *
     * @param sensitiveSize 敏感信息长度
     */
    public static String address(final String address, final int sensitiveSize) {
        if (StringUtils.isBlank(address)) {
            return "";
        }
        final int length = StringUtils.length(address);
        return StringUtils.rightPad(StringUtils.left(address, length - sensitiveSize), length, "*");
    }

    /**
     * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
     */
    public static String email(final String email) {
        if (StringUtils.isBlank(email)) {
            return "";
        }
        final int index = StringUtils.indexOf(email, "@");
        if (index <= 1) {
            return email;
        } else {
            return aroundPad(email, 1, index, SymbolConstants.STAR);
        }
    }

    /**
     * [银行卡号] 前六位，后四位，其他用星号隐藏每位1个星号<例子:6222600**********1234>
     */
    public static String bankCard(final String cardNum) {
        return aroundPad(cardNum, 6, 4, SymbolConstants.STAR);
    }

    /**
     * [公司开户银行联号] 公司开户银行联行号,显示前两位，其他用星号隐藏，每位1个星号<例子:12********>
     */
    public static String cnapsCode(final String code) {
        if (StringUtils.isBlank(code)) {
            return "";
        }
        return StringUtils.rightPad(StringUtils.left(code, 2), StringUtils.length(code), "*");
    }

    /**
     * 【密码】密码的全部字符都用*代替，比如：******
     *
     * @param password
     * @return
     */
    public static String password(String password) {
        if (StringUtils.isBlank(password)) {
            return "";
        }
        return StringUtils.rightPad(SymbolConstants.EMPTY, StringUtils.length(password), SymbolConstants.STAR);
    }

    /**
     * 环绕填充。例：13845678125->138*****125
     * @param num
     * @param beginSize
     * @param endSize
     * @param padStr
     * @return
     */
    public static String aroundPad(final String num, final int beginSize, final int endSize, String padStr) {
        if (StringUtils.isBlank(num)) {
            return num;
        }
        return StringUtils.left(num, beginSize).concat(StringUtils.substring(StringUtils.leftPad(StringUtils.right(num, endSize), StringUtils.length(num), padStr), beginSize, StringUtils.length(num)));
    }




    /**
     * [香港身份证号] 显示最后四位，其他隐藏。共计18位或者15位。<例子：*************5762>
     */
    public static String hkIdCardNum(final String num) {
        return aroundPad(num, 1, 1, SymbolConstants.STAR);
    }

    /**
     * [香港手机号码] 前三位，后四位，其他隐藏<例子:138******1234>
     */
    public static String hkMobilePhone(final String num) {
        return aroundPad(num, 1, 1, SymbolConstants.STAR);
    }

    public static void main(String[] args) {
        String num = "13845678125";
//        String num = "430721199101136419";
        System.out.println(StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*"));
        System.out.println(aroundPad(num, 4,4, "*"));
        System.out.println(StringUtils.rightPad(SymbolConstants.EMPTY, StringUtils.length(num), SymbolConstants.STAR));
    }

}