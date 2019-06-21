package com.minlia.module.encryptbody.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by garen on 2018/7/18.
 */
@Slf4j
public class RSAEncryptUtil {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo0BlgdQtsk6Zs66/ftJXWkCwFqzYzDRb7l60NNCWIYKiFsbPBpJxMg9i4a4ADPiecjcaMSNt+/+MRTwZ+QsD8zf/T6LM1uGEc1f9tCLTlKx/+sC/Sc5J2558/VpVBI6xyB8azXOU9k+gZ/jM2sxd4fgbh9KijO12mDYg3fM9Jy7try8MPuVO35rdqdGp2HL5BXaPrQVils+kNMDZvikQ13nr3jjf/F03VWo1uSLviHeUquAvLUiylpX1SMNpOkmxzepMRITF51vqsPV0iGUAhNimqvcVDUn4anyQZ2snRfLRfIu5JKyfJbaNTIKWYhHMaClHnl7H0+bIz35cTWcofwIDAQAB";
    public static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCjQGWB1C2yTpmzrr9+0ldaQLAWrNjMNFvuXrQ00JYhgqIWxs8GknEyD2LhrgAM+J5yNxoxI237/4xFPBn5CwPzN/9PoszW4YRzV/20ItOUrH/6wL9Jzknbnnz9WlUEjrHIHxrNc5T2T6Bn+MzazF3h+BuH0qKM7XaYNiDd8z0nLu2vLww+5U7fmt2p0anYcvkFdo+tBWKWz6Q0wNm+KRDXeeveON/8XTdVajW5Iu+Id5Sq4C8tSLKWlfVIw2k6SbHN6kxEhMXnW+qw9XSIZQCE2Kaq9xUNSfhqfJBnaydF8tF8i7kkrJ8lto1MgpZiEcxoKUeeXsfT5sjPflxNZyh/AgMBAAECggEAbNvDFDidJBofid6L2RB/qTIbeXNWuYxshN3nwpQYmE3YseFuKU67Zos1/5EcfYnHdhfiQkQC0hgMvBogo53By4xalgrUFrGYtaVGCrlyZuBEErw2kguZ2nWrmPGEYZ6ZU/3RzJA3E6vzJYGVcIz4SXc9PGurctfhzgvCDN5mt9oVgnHOU10GRvs0WYYtjc4IXvuhDeTbHLxiAQWEEVu4uPIN4QsjbeYnSHZV3np5uLeQ4AmmwoVmifvH8SWkVOeGy/5Nq37SlW0wbzFiQPjIaW2VI7BTlFk6yGpJ0eepHDJD1SOsIaOLUfyvolAX4iz5jKgfKohujE5McK8ZXft4aQKBgQDOnSRgHm/4Atn4gELRG5wkn2bp/S4fPsHvkrkLQ6K2UbCNT+aVlK83xQLQ/NHH9Zqoe2mDjdgZZtN0U4n37Y3WmtnmOe9zwiPrMqqlAC6mBknmkxzVvnv+HcF5wtDketYunfzUrfmdsPQ2CJyfdnq+iPlHiFHyBWvzK3iA36MrMwKBgQDKReIPbjEbiFZInvFypovMrYFSP5uxcP/z2xiSVFr7BxFgdY9DgQqWr1SX4H/ycMqN6MQpPBNfvalulnHOK0qYAVpCExARmEDfqiVtoBtaDcSWe3cEadugRrbYjBKGfTpKB9Si1bS9aZUCFA7FJ7MglZty2J6oWyAMhLnxcs9thQKBgQCSJxivIXjE8IdtwJUDREucoGF1TZINqiAXrytUa4CzBXAlVgrfbrDPe341hVGD6BmB6uLFm1XtCg60L8/d4F3AHAPdVsgqk39DBbEuTMWyzaoC/7ea/0bIiBXvYr7PkJDnovxRGcwEH0Nw4YChUHcdRtlHH4gP8oPdHLf+ObQ7fwKBgDbycu10JpzvFVlW7AoUGgf43heEtfum0bDhxLQDfE+S5PKKHOfehwzWBinXz4oBf9S69YaqDi1E7WDgN3wlIHdYbrdiTEwrSvw3UUGNa8Cz2sJyOauWQhYw/3yl2GZ2YD46EFZHrXiC1PqijjyagzuHyJmTJM5hKryb1ZM0eAqtAoGAXnPnD9Dri9+cTor16jKrCUH8PFMZ4TCFYRzWAr/+ayCylMdaxFjvVcaE+UJpyVw62FCgYV70Zqtsj1E4UGTp1lPM7RNudFw8FC9Pca95UYheCQc6/YMA1ACDR3hnnAUU0925T9/QhESCVQyEaFZVyBiPLZRygSWiIpAr6zKojDA=";

    /**
     * 随机生成公钥和私钥
     */
    public static Map<String, Object> getRandomKey() {
        //获得对象 KeyPairGenerator 参数 RSA 2048个字节
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGen.initialize(2048);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();

        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //公私钥对象存入map中

//        byte[] publicKeyBytes = publicKey.getEncoded();
//        byte[] privateKeyBytes = privateKey.getEncoded();
//
//        String publicKeyBase641 = Base64Utils.encodeToString(publicKey.getEncoded());
//        String privateKeyBase641 = Base64Utils.encodeToString(privateKey.getEncoded());

        Map<String, Object> keyMap = new HashMap(2);
        keyMap.put(PUBLIC_KEY, Base64Utils.encodeToString(publicKey.getEncoded()));//获取公钥Base64编码
        keyMap.put(PRIVATE_KEY, Base64Utils.encodeToString(privateKey.getEncoded()));//获取密钥Base64编码
        return keyMap;
    }

    /**
     * 通过字符串生成公钥
     */
    public static RSAPublicKey getPublicKey(String publicKeyData) {
        RSAPublicKey publicKey = null;
        try {
            byte[] keyBytes = Base64Utils.decodeFromString(publicKeyData); //将字符串Base64解码
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    /**
     * 通过字符串生成私钥
     */
    public static RSAPrivateKey getPrivateKey(String privateKeyData) {
        RSAPrivateKey privateKey = null;
        try {
            byte[] keyBytes = Base64Utils.decodeFromString(privateKeyData); //将字符串Base64解码
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);//创建x509证书封装类
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);//指定RSA
            privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);//生成私钥
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    /**
     * 加密
     * @param bytes
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] bytes, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] encrypt(String content) {
        return encrypt(content.getBytes(), getPublicKey(PUBLIC_KEY));
    }

    public static String encryptBase64(String content) {
        return Base64.encodeBase64String(encrypt(content.getBytes(), getPublicKey(PUBLIC_KEY)));
    }


    /**
     * 解密
     * @param bytes
     * @param privateKey
     * @return
     */
    public static byte[] decrypt(byte[] bytes, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] bytes, String privateKey) {
        RSAPrivateKey rsaPrivateKey = getPrivateKey(privateKey);
        return decrypt(bytes, rsaPrivateKey);
    }

    public static String decrypt(String content, String privateKey) {
        RSAPrivateKey rsaPrivateKey = getPrivateKey(privateKey);
        return decrypt(content, rsaPrivateKey);
    }

    public static String decrypt(String content, PrivateKey privateKey) {
        return new String(decrypt(Base64Utils.decodeFromString(content), privateKey));
    }

    public static String decrypt(String content) {
        return decrypt(content, PRIVATE_KEY);
    }

//    public static String content = "{\"receiveTime\":\"1552988611860\",\"smsContent\":\"1411\",\"smsNumber\":\"13048989908\",\"deviceId\":\"c9a8d891add871b0\"}";
    public static String content = "{\"name\":\"1552988611860\",\"value\":\"1411\"}";

    public static void main(String[] args) throws Exception {
        //随机生成公钥和私钥
        Map<String, Object> map = getRandomKey();
        System.out.println(map.get(PUBLIC_KEY).toString());
        System.out.println(map.get(PRIVATE_KEY).toString());

//        System.out.println("排序前：" + content);
//        String jsonSorted = jsonSortToString(content, "&", "sign");
//        System.out.println("排序后：" + jsonSorted);

        String encryptedBase64 = encryptBase64(content);
        System.out.println("加密后：" + encryptedBase64);

        String decrypted = decrypt(encryptedBase64);
        System.out.println("解密后：" + decrypted);
    }

//    public static String jsonSortToString(String jsonStr, String joiner, String... ignores) {
//        Gson gson = new Gson();
//        Map<String, Integer> map = gson.fromJson(jsonStr,Map.class);
////        map.keySet().forEach(System.out::println);
//        StringJoiner sj = new StringJoiner(joiner);
//        List list = Arrays.asList(ignores);
//        map.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(x -> {
//            if (CollectionUtils.is(list) && !list.contains(x.getKey())) {
//                sj.add(x.toString());
//            }
//        });
//        return sj.toString();
//    }

}
