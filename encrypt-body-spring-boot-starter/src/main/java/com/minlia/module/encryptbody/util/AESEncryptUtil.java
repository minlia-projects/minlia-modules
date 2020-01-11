package com.minlia.module.encryptbody.util;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

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

    public static String generateIv() {
//        Cipher cipher = Cipher.getInstance(SymmetricAlgorithm.AES.getValue());
//        SecureRandom randomSecureRandom = new SecureRandom();
//        byte[] iv = new byte[cipher.getBlockSize()];
//        randomSecureRandom.nextBytes(iv);
//        return new String(iv, "UTF-8");
        return RandomStringUtils.randomAlphanumeric(16);
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

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException {
//        String key = "1UqxUHXS5flqZO7Bv+B1kQ==";
//        String iv = "YWx1B0FSDiDAvx==";
        String key = "zZyWl39ow6T0i4zIWJbfkA==";
        String iv = "YSqr2v2jOsAZtQZi";

        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, key.getBytes(), iv.getBytes());

        System.out.println(encrypt("{ \"beginDateTime\": \"2019-12-06T19:12:47\", \"hours\": 8 }", key, iv));
        System.out.println(decrypt("G6/RUN+/QW+Xuu5XkPXk46muHEBIucw+GOubnnSpITOL+QxMrEhKLSMyUwZZwXsbdjdCDszzsmLGP8FtMiXMJX9hKTt0FFTMFq8t28Ixjvx9D+lUYIx+4Ykj0ZGKb3VHqnX9J5pQmZrF1ncTR5BMpHr4MVmAi6SYfxldc66JNYUEezcWnZPhThNnsoMa26OtYgKEBZt+UMyECTlihkGEmp2ebNwS0BUZdlgZG1WjXRq4bQrx0rEF9EZwhVcRWY5W3GvRQ6agu0WvKgBmTWdy71/8Bi3IVUcCcyvW0xjumH3qUC9fU5mfjQK81cJCoCGPt8UR2tpW7HLqiI4IOJKrPAbJUe2eDiE5XGEtfYp/BfjVe31Yg+/GJEyqp48sLRtP", key, iv));
        System.out.println(decrypt("ctFB2w7wYr3H5i2ZGTya8xP1ua5ZugX+RIvIc0W5IU2Rd/0Q6leAOe1ZuMvoPav6+5O8VOvWO0Bx8KENxVeG9d340gRkZjXijMrWtXVdQhlG2AcFZ3JiFgaZPTxMHuj51M7yq9O4kaOyGeoGx9FUzNb53n0wVytIpJ9ZW/xwWnI/DtLBLNHFjNeWfFVjQZxCCxBxUGoBUtDylgyjbVlZ9kk7mNKc28ynuYA1L+iaTfSEM+CL+RTW+0cvr/m0tcpe/u9u/V2jEAkvlDlaFYrxZi3IpArMXU2ghbgZD1i1zxazMh25eOp/ubPsmoPBOLaQ7fhaEosV6rnlVOvrECVeEF/pFto2OHuUZMo/aaJxcSX6AML+31ljXPLH8nhXvh1i", key, iv));


//        {
//            "data":"utp2P0b8AoH8BJyi0VXwklNTl/q03QRQPtjhs8Tg8AijOdwLO+3hgYLPpcA/jwrtLbiHBv9CK7sqFv6DcXNNkQ=="
//        }

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