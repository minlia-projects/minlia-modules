package com.minlia.module.encryptbody.util;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/7/5 7:33 PM
 */
public class XORCryptUtil {

    private static final int numOfEncAndDec = 0x99; //加密解密秘钥

    public static String decrypt(String content, int key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            sb.append((char) (key ^ content.charAt(i)));
        }
        return sb.toString();
    }

}