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

        System.out.println(decrypt("rGANuisaG7SWuGQc0DQ1k1yV4exhxXEHSf0IZeBexCKZRBpgLK0+0j3sYE9A0PvwL53P48zcRYuXQEs9ynjnd0tt0GmqVGzVH2JwjY3+4VVaAGfLP91iApM0N1NDiV9KRQCpOBh+CSglDmnPHaNl11gyra8gPBVULZJqQywuHpPRWlFni0faD9dMPf+fTzEA5Ov4i27v3DoOFR0HCdxgzNbu5Wc6bLxRoxr97DC/HiA0qytuGLZTbsfSAq8czxI6vHxBm7dFl6dSbg6nEMdnIVhryBcgGXJ4pSrcv8gqTt6tLMoBI7QTKu/VNztpygqZr5FLCfvQa1gfREX2iZ8MBaqT9Sx0Y7dBdpUvqTiADB1B4tlwZFqfq3HMSXn3KrPpRjoc/H4j88z31uPJHJS8fUbX9iav05QF3n7ndqK9Yml7GP9aJq2jsgzEtARZ7GokmYrii4liQHBTunot3FfkQzIFXRNX3fGrg4TwtxqjKvzQfk10rue4SjcqwWKEeIdHMzhN+Q7tMTmFgVzg2x53n4+DH7Ff+N+/tRpziMiDeQGWniEps7c+57qiCO6HRB8CZ/D8gZjxjKgXNhj38AW7aGcZGxggfl5eneKNkgWbub6tTSdmuoVt/BoTA/Ahut74vhXitl+GWnJa4JrN08nvzfnmz/vxdoJU2Nr1un1+LoA=", key, iv));

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