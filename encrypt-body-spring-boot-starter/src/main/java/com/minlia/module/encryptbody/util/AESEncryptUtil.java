package com.minlia.module.encryptbody.util;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.Cipher;

public class AESEncryptUtil {

    /**
     * 生成密钥
     *
     * @return
     */
    public static String generateKey() {
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        return Base64Encoder.encode(key);
    }

    /**
     * AES加密
     *
     * @param data 字符串内容
     * @param key  密钥
     */
    public static String encrypt(String data, String key) {
        return aes(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * AES解密
     *
     * @param data 字符串内容
     * @param key  密钥
     */
    public static String decrypt(String data, String key) {
        return aes(data, key, Cipher.DECRYPT_MODE);
    }

    /**
     * AES加密/解密 公共方法
     *
     * @param data 字符串
     * @param key  密钥
     * @param type 加密、解密
     */
    private static String aes(String data, String key, int type) {
        AES aes = new AES(key.getBytes());
        if (type == Cipher.ENCRYPT_MODE) {
            return aes.encryptBase64(data);
        } else {
            return aes.decryptStrFromBase64(data);
        }
    }

    /**
     * AES加密
     *
     * @param data 字符串内容
     * @param key  密钥
     */
    public static String encrypt(String data, String key, String iv) {
        return aes(data, key, iv, Cipher.ENCRYPT_MODE);
    }

    /**
     * AES解密
     *
     * @param data 字符串内容
     * @param key  密钥
     */
    public static String decrypt(String data, String key, String iv) {
        return aes(data, key, iv, Cipher.DECRYPT_MODE);
    }

    /**
     * AES加密/解密 公共方法
     *
     * @param data 字符串
     * @param key  密钥
     * @param iv
     * @param type 加密、解密
     */
    private static String aes(String data, String key, String iv, int type) {
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, key.getBytes(), iv.getBytes());
        if (type == Cipher.ENCRYPT_MODE) {
            return aes.encryptBase64(data);
        } else {
            return aes.decryptStrFromBase64(data);
        }
    }

//    public static void main(String[] args) {
//        String key = "zZyWl39ow6T0i4zIWJbfkA==";
//        String iv = "YSqr2v2jOsAZtQZi";
//
//        String data = "{\"name\":\"sadf\", \"value\":\"111\"}";
//
//        //加密后
//        String encryptStr = encrypt(data, key, iv);
//
//        //重新封装
//        data = String.format("{\"data\":\"%s\"}", encryptStr);
//        System.out.println("加密后的参数：" + data);
//        System.out.println("解密后的参数：" + decrypt(encryptStr, key, iv));
//    }

    public static void main(String[] args) {
        String key = "zZyWl39ow6T0i4zIWJbfkA==";
        String iv = "YSqr2v2jOsAZtQZi";

        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, key.getBytes(), iv.getBytes());
        String data = "{\"cellphone\":\"99990002\"}";

        System.out.println(decrypt("nj2PSqPYvpR0KQocwkTOvbUH2PeRWeQPwqX7bJ9JbvLqUMp8S8tBEjiljIly4NLRQhC1vGoc3l+ObmHoBplDGMwki8ZOf/Th0MbGGQQWI0M=", key, iv));

//        System.out.println(encrypt(data, key, iv));
//        System.out.println(decrypt(encrypt(data, key, iv), key, iv));
//
//        System.out.println(aes.encryptBase64(data));
//        System.out.println(aes.decryptStrFromBase64(aes.encryptBase64(data)));
//
//        System.out.println(aes.encryptHex(data));
//        System.out.println(aes.decryptStr(aes.encryptHex(data)));


    }

}