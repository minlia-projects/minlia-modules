package com.minlia.module.data.util;

import com.minlia.module.common.constant.SymbolConstants;
import org.apache.commons.lang3.StringUtils;

public class SensitiveInfoUtils {

    /**
     * 全部字符都用*代替。<例子：*************>
     *
     * @param s
     * @return
     */
    public static String all(final String s, final String pad) {
        if (StringUtils.isBlank(s)) {
            return SymbolConstants.EMPTY;
        }
        return StringUtils.rightPad(SymbolConstants.EMPTY, StringUtils.length(s), pad);
    }

    /**
     * 环绕填充。<例子：138*****125>
     *
     * @param s
     * @param beginSize
     * @param endSize
     * @param pad
     * @return
     */
    public static String around(final String s, final int beginSize, final int endSize, String pad) {
        if (StringUtils.isBlank(s)) {
            return SymbolConstants.EMPTY;
        }
        return StringUtils.left(s, beginSize).concat(StringUtils.substring(StringUtils.leftPad(StringUtils.right(s, endSize), StringUtils.length(s), pad), beginSize, StringUtils.length(s)));
    }

    /**
     * 显示开始几位，其他隐藏。<例子：5762*************>
     * @param s
     * @param size
     * @param pad
     * @return
     */
    public static String left(final String s, final int size, final String pad) {
        if (StringUtils.isBlank(s)) {
            return SymbolConstants.EMPTY;
        }
        return StringUtils.rightPad(StringUtils.left(s, size), s.length(), pad);
    }

    /**
     * 显示末尾几位，其他隐藏。<例子：*************5762>
     * @param s
     * @param size
     * @param pad
     * @return
     */
    public static String right(final String s, final int size, final String pad) {
        if (StringUtils.isBlank(s)) {
            return SymbolConstants.EMPTY;
        }
        return StringUtils.leftPad(StringUtils.right(s, size), s.length(), pad);
    }

    /**
     * 隐藏末尾几位，其他显示。<例子：****4564564565762>
     * @param s
     * @param size
     * @param pad
     * @return
     */
    public static String rightPad(final String s, final int size, final String pad) {
        if (StringUtils.isBlank(s)) {
            return SymbolConstants.EMPTY;
        }
        final int length = StringUtils.length(s);
        return StringUtils.rightPad(StringUtils.left(s, length - size), length, pad);
    }


    /**
     * 隐藏开始几位，其他显示。<例子：57624354564565***>
     * @param s
     * @param size
     * @param pad
     * @return
     */
    public static String leftPad(final String s, final int size, final String pad) {
        if (StringUtils.isBlank(s)) {
            return SymbolConstants.EMPTY;
        }
        final int length = StringUtils.length(s);
        return StringUtils.leftPad(StringUtils.right(s, length - size), length, pad);
    }



    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     */
    public static String chineseName(final String fullName, final String pad) {
        return left(fullName, 1, pad);
    }

    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     */
    public static String chineseName(final String familyName, final String givenName, final String pad) {
        if (StringUtils.isBlank(familyName) || StringUtils.isBlank(givenName)) {
            return SymbolConstants.EMPTY;
        }
        return chineseName(familyName + givenName, pad);
    }

    /**
     * [身份证号] 显示最后四位，其他隐藏。共计18位或者15位。<例子：*************5762>
     */
    public static String idCardNum(final String s, final String pad) {
        return right(s, 4, pad);
    }

    /**
     * [固定电话] 后四位，其他隐藏<例子：****1234>
     */
    public static String fixedPhone(final String s, final String pad) {
        return right(s, 4, pad);
    }

    /**
     * [手机号码] 前三位，后四位，其他隐藏<例子:138******1234>
     */
    public static String mobilePhone(final String num, final String pad) {
        return around(num, 3, 4, pad);
    }

    /**
     * [地址] 只显示到地区，不显示详细地址；我们要对个人信息增强保护<例子：北京市海淀区****>
     *
     * @param s 敏感信息长度
     */
    public static String address(final String s, final int size, final String pad) {
        return rightPad(s, size, pad);
    }

    /**
     * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
     */
    public static String email(final String email, final String pad) {
        if (StringUtils.isBlank(email)) {
            return "";
        }
        final int index = StringUtils.indexOf(email, "@");
        if (index <= 1) {
            return email;
        } else {
            return around(email, 1, index, pad);
        }
    }

    /**
     * [银行卡号] 前六位，后四位，其他用星号隐藏每位1个星号<例子:6222600**********1234>
     */
    public static String bankCard(final String cardNum, final String pad) {
        return around(cardNum, 6, 4, pad);
    }

    /**
     * [公司开户银行联号] 公司开户银行联行号,显示前两位，其他用星号隐藏，每位1个星号<例子:12********>
     */
    public static String cnapsCode(final String s, final String pad) {
        return left(s, 2, pad);
    }

    /**
     * 【密码】密码的全部字符都用*代替，比如：******
     *
     * @param s
     * @return
     */
    public static String password(String s, final String pad) {
        return all(s, pad);
    }

    /**
     * [香港身份证号] 显示最后四位，其他隐藏。共计18位或者15位。<例子：*************5762>
     */
    public static String hkIdCardNum(final String num, final String pad) {
        return around(num, 1, 1, pad);
    }

    /**
     * [香港手机号码] 前三位，后四位，其他隐藏<例子:138******1234>
     */
    public static String hkMobilePhone(final String num, final String pad) {
        return around(num, 1, 1, pad);
    }



    public static void main(String[] args) {
//        String num = "13845678125";
        String num = "456576547654877668";
//        String num = "北京市海淀区三大放送大";
        System.out.println(around(num, 3, 4,"*"));
        System.out.println(left(num, 3, "*"));
        System.out.println(right(num, 4, "*"));
        System.out.println(leftPad(num, 4, "*"));
        System.out.println(rightPad(num, 4, "*"));
    }

}