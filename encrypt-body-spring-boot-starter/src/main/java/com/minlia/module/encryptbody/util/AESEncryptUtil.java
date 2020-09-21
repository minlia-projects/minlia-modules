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

<<<<<<< HEAD
        System.out.println(decrypt("ngbgROaKIzpFHId8CMWUHSeGDCFqxaXpAKGTFPhm6rmbflchiQZLrYq6TPRpLE1igQyHtnO7oJqVNbpPvwBIrh3CFcQoSQ8PZHKK5DoodDYG7b33DE5g0akT45+q1JeE8oMCcPA3HqxIhgBXltNnzNbP4lS++DPSAWmP3nKWPesv8coVHvudmE390wGKxqEJv8aMbU6DPXdKgN65bKZfeeH0BH/n7v8l9Q2QSBj6N7NfiIz0+/Rkh7Hqu7y6ukxaFbL7Hzz8wh+yRx0DyJpZFnzOqdLQHrQ5WKCQcCqsGSKWxQSwgGrkxwOJ8p2qwcclkomUqJUyNbhAJABaqieilNMTInIYGlvkqDFE0jt3671tCqJCur4xBWSZstEylSGOwSCtpbucY/BwkzVqpPBmWUXRf+NbDLN2RnNVotbDdG4M0uyytIiFQ1QbIFJfqBQOn0O/aQse1RjmBlJjKYAuhK0c/1y9B+10Q7ke/HW3a8cwmfOhloYPYYJhzS8rgTDaZWHuYmdfh2iDw13WBScZtC52PBffENklta+slwW4Yhmi0Gl+xqbajeLrwKwCa4Ap1rYPCDbddSkrmwMULPIcp6o9ykP4t3a5xo4eXnrJKL0nYFU6abWs8uNOhx2FO7syxcsot/xuh9Ezo4Ntzos4Da4z+OB4c17sAgnAUariffng8YsOO0jZb5BTx+m+O5UshBIZoZql0jumNM+3MjmJs/ajOKC2o0oS+yhjgn39Ml6mxJBPWduLlFz2PkPWo/qqeW2GDt3qqsEdW4qIvGqEgjgp/SvFaH6QdcAppk2res1RzNU5cxUlVOQjXiz7MM/AoHFjlU/x+b+orkTf6I5m1J9+7kDWgRPRyAyES0hUJ3pJktNQ/LsJFvbYUOAA/+yjHyj9IJWkc+wxpxLkaA0mcZyG079H2XIEglaF/1i4g7D7VLLrhZb5p5C8DD350WXSqXXVwF0A1z/flc6bre0g/lO8L/5SRouKE+TTVRioPm373kRNFe9FN6z4MucdLybAAr2ZAKjJJxBHJd8VM/ttfUOjAjQ3hvpGnbXThbuP2xsHk9duFCIiCTiavPD/Cmj8YBalfYd2ed3HRAQiUiWpBoIlJqoZe7tPfusLlEeZKC6ZkGSmbu68OAotto6vtznT", key, iv));
=======
        System.out.println(decrypt("RVfF6eKUYwBtEh/CB4GfYu6VAJuMHw9mOKrtG+2EeaPfVRIjRGcFKSZQT8Uijfa6CZAxPxg317g5xLh/so42+hQzSow9K6UBn6OsjTCaW2Lq5rXSiMsP8ZaUXer7JKfJHJ/7dVER3DaEJzMQveJFZQtA3ZPp0ZTnkvk6H79bNnSuyytmm2oVQaGQUQxzA7mGS/0mNJhsBqvmeWs9GSmKUKG5Dnf5mYxeaa4K9uGY89u2KIhD2CaOw5vqk1zKcSV6nm1GJ2DFsO69epMP5824SWWcTYpNoBb7RXOSiyaePpmAJOkuIyVGL2Y8tSYbFle3BLJA3hUWNCcDvpV+raAzq7B/BdBKE+0KV6VxKwgkgAlWE5xbBo7alvNm9HdkKcI9NwAqspcdyQ81G9nW950J0EFuWWR4HSfkA9AfhSlImAFMjACBBqa0Ut0Zfa2ol9q9AbB++wmNc1+x9+/YCkGfVw==", key, iv));
>>>>>>> dev/garen

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