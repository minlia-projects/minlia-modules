package com.minlia.module.encryptbody.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
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
    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDCLh48Wq5I6atYb0kurC09Q535b37dZpJR+UElX5cEfNMiVQwcK3IU/74lwWC6hCC90iFPjIcK+p/+XA2FBSQDgcW124rh+KTMOgesxLyYrjnrfPyUUuBm3MUQnTAytV6IAVMvq7s0N+R/zXKxpYBypMgDl5XYbOvUE5SJwTvhIQIDAQAB";
    public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMKz0Z18guQrLPdCJdtPK0vR7SpeBie0LBELf7atQYyKJ3WgX6CNGmrFDlsQWp17X0T/s9KzaHbpc2I2PvlZoBz7CHnfDGwi7p/sI9dVNeOdfFa5drsqmK60rGrj6BzveGgPDJJoXPb5LbfbMDN34hG/QGfuDsEkfUbYYB+apC0ZAgMBAAECgYEAuFiPWGBCggyLJ5T+yPXdlY0u05VwmHkT3BOaGXlTfeB02f89a4MOBxeKrxf94+ui2W6NcSqi9yu0LsITv/1nBUK4oBOjC75garFjYjSDTdGLD96BDOahVzqKMY8eE9XEZQEX4bhj6ZDa5q8nvGnkant0o5yZzGeT2ZMK69SMdjECQQDfR2TBcNHhJLp3I8qzuV8iYsZnyUEU+I47DRRv9qKl1WWAW3EJ7cyv69OkAQHwJy2BDQDuSFgn6T/lx4N1yCmlAkEA3zxXROmK5T7shE1HYmt3AdTSFnAcR/lKbd/+iuIhaN77MOzEaKvlDxRv5Jh4G+lLkwHFSAaRYRPmkv6qmBqTZQJBAN3uA6r2rdaggCrty4wqg/IUxerhMqxahl0Rmi/TsUUuQA5+VXQuBpcRy7KnQbrn5iXwu+0cwWsiP93wGq3Wv/UCQCvw0Ky7258sN5oDLB3vUUmG/qN0Bd0U8NWX1Z64zCK8YW1L7Y086KWDPFMev+WekkWpf4+h21PkeupMPoAaGxECQGDnx0/UA972Y4wZfP+jjCG74UdqSH51+FRrLOzLUuhvQSiKYmSpZXpWzOKa0ZnmAuIfjWIen/VmjuQ6iaLiWR0=";

//    public static final String PUBLIC_KEY = "Cosh2c9muUh3DZ/ubIDCO9yrfHlkimh1gygdsCMqtyuZt3296y8SgJLUyVf62g/1z+5t8i0Ho+Oww/SKiUl6S3rycMy8m4GSRf2tedvL0k5lZqov0nZP0HqH+s71smWu+EbpoZuHGmdAHqPDdZyUdRJ8KDaC65hYyKzkc7J6MibBEmC4bR5BfkMPT91QRT1SWZSvLy5hzYLum64mbNdVUdYznW0Sin0OVOoAN4ghKYQrL7TthIpYRbPYLtEiICuTsHmwjaUUmuRaaz/4VL7NP4uDaBOSdMYdqetme2DhaIH7KncKAH4dU1nhtL2OUSsMuc6nP/WDM8AhZ/iZdFFAkQ==";
//    public static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKt5qXNivS5vD8P+aUg+fa0zCkp5ReOYKAqIS3N346vqI8mR9XznT8fo2Y2NZEudNkznaStB8T7sFVl59IPPhJ+ag+KkPAWsuCaLa6hTF6XbF3vM2ZX11F/KaZ9pLBWtioVA3ycmwzrlzYLFpe2jK71LNHU/khbj2iStzquzSusFAgMBAAECgYBhcJQK3mv0S+YgkJxREjmmRx+s7Db7usXeHk8JwXHdigJmE4v1OxhJ2BZv6o24rhBiKQnJLAKjp9IF81gv4qdQgDn4/ZcCmkssGc4k0s96X7S9ODk2u8H3xVX3Wq691C/ia4Q3Bdceh/XWp3fFOGonJG/rV5l9lmVlrKFqOzMK7QJBANw/4V9ljamlFHBr0fYMDkl0lb9XMsQ32kcI5NOyEoHzHi3CYeI8x8QONsIU2HRi8bENsTtv8v40OU250NQboasCQQDHTwsYVaVhF/1qGTSbuL5dhAqG+PP4aCaSgngiSvSR8UjmuoICu1Y7fQalFCW2u1M2EgHduono/vWxdr5lF1YPAkAca93gWpF6R8QocbFpZ8rcLcA5Lb5uVarEKeCldMAwprfJPeE1uZQpt4/XYatA8ZewukyZl+uqZADVB9pr1Jj7AkAjgKb/TLJK21UewyKl4oC2c8DUTiBXTWYhjvALW8K5BHvx7aMfDXPM4PaADViydifaZ9bBQmsyf8gQNpCvvTptAkA8x7A6STY9X6gr5ptjFJehVKN499mPQ6c4JxMyVDealYtFPTMrVL0RGyYAOdfTz4nEDzT5pLPvz1p+JsSjHRQF";


//    public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo0BlgdQtsk6Zs66/ftJXWkCwFqzYzDRb7l60NNCWIYKiFsbPBpJxMg9i4a4ADPiecjcaMSNt+/+MRTwZ+QsD8zf/T6LM1uGEc1f9tCLTlKx/+sC/Sc5J2558/VpVBI6xyB8azXOU9k+gZ/jM2sxd4fgbh9KijO12mDYg3fM9Jy7try8MPuVO35rdqdGp2HL5BXaPrQVils+kNMDZvikQ13nr3jjf/F03VWo1uSLviHeUquAvLUiylpX1SMNpOkmxzepMRITF51vqsPV0iGUAhNimqvcVDUn4anyQZ2snRfLRfIu5JKyfJbaNTIKWYhHMaClHnl7H0+bIz35cTWcofwIDAQAB";
//    public static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCjQGWB1C2yTpmzrr9+0ldaQLAWrNjMNFvuXrQ00JYhgqIWxs8GknEyD2LhrgAM+J5yNxoxI237/4xFPBn5CwPzN/9PoszW4YRzV/20ItOUrH/6wL9Jzknbnnz9WlUEjrHIHxrNc5T2T6Bn+MzazF3h+BuH0qKM7XaYNiDd8z0nLu2vLww+5U7fmt2p0anYcvkFdo+tBWKWz6Q0wNm+KRDXeeveON/8XTdVajW5Iu+Id5Sq4C8tSLKWlfVIw2k6SbHN6kxEhMXnW+qw9XSIZQCE2Kaq9xUNSfhqfJBnaydF8tF8i7kkrJ8lto1MgpZiEcxoKUeeXsfT5sjPflxNZyh/AgMBAAECggEAbNvDFDidJBofid6L2RB/qTIbeXNWuYxshN3nwpQYmE3YseFuKU67Zos1/5EcfYnHdhfiQkQC0hgMvBogo53By4xalgrUFrGYtaVGCrlyZuBEErw2kguZ2nWrmPGEYZ6ZU/3RzJA3E6vzJYGVcIz4SXc9PGurctfhzgvCDN5mt9oVgnHOU10GRvs0WYYtjc4IXvuhDeTbHLxiAQWEEVu4uPIN4QsjbeYnSHZV3np5uLeQ4AmmwoVmifvH8SWkVOeGy/5Nq37SlW0wbzFiQPjIaW2VI7BTlFk6yGpJ0eepHDJD1SOsIaOLUfyvolAX4iz5jKgfKohujE5McK8ZXft4aQKBgQDOnSRgHm/4Atn4gELRG5wkn2bp/S4fPsHvkrkLQ6K2UbCNT+aVlK83xQLQ/NHH9Zqoe2mDjdgZZtN0U4n37Y3WmtnmOe9zwiPrMqqlAC6mBknmkxzVvnv+HcF5wtDketYunfzUrfmdsPQ2CJyfdnq+iPlHiFHyBWvzK3iA36MrMwKBgQDKReIPbjEbiFZInvFypovMrYFSP5uxcP/z2xiSVFr7BxFgdY9DgQqWr1SX4H/ycMqN6MQpPBNfvalulnHOK0qYAVpCExARmEDfqiVtoBtaDcSWe3cEadugRrbYjBKGfTpKB9Si1bS9aZUCFA7FJ7MglZty2J6oWyAMhLnxcs9thQKBgQCSJxivIXjE8IdtwJUDREucoGF1TZINqiAXrytUa4CzBXAlVgrfbrDPe341hVGD6BmB6uLFm1XtCg60L8/d4F3AHAPdVsgqk39DBbEuTMWyzaoC/7ea/0bIiBXvYr7PkJDnovxRGcwEH0Nw4YChUHcdRtlHH4gP8oPdHLf+ObQ7fwKBgDbycu10JpzvFVlW7AoUGgf43heEtfum0bDhxLQDfE+S5PKKHOfehwzWBinXz4oBf9S69YaqDi1E7WDgN3wlIHdYbrdiTEwrSvw3UUGNa8Cz2sJyOauWQhYw/3yl2GZ2YD46EFZHrXiC1PqijjyagzuHyJmTJM5hKryb1ZM0eAqtAoGAXnPnD9Dri9+cTor16jKrCUH8PFMZ4TCFYRzWAr/+ayCylMdaxFjvVcaE+UJpyVw62FCgYV70Zqtsj1E4UGTp1lPM7RNudFw8FC9Pca95UYheCQc6/YMA1ACDR3hnnAUU0925T9/QhESCVQyEaFZVyBiPLZRygSWiIpAr6zKojDA=";

    /**
     * 生成密钥对：密钥对中包含公钥和私钥
     * @return 包含 RSA 公钥与私钥的 keyPair
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static KeyPair getKeyPair() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");    // 获得RSA密钥对的生成器实例
        SecureRandom secureRandom = new SecureRandom(String.valueOf(System.currentTimeMillis()).getBytes("utf-8")); // 说的一个安全的随机数
        keyPairGenerator.initialize(2048, secureRandom);    // 这里可以是1024、2048 初始化一个密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();   // 获得密钥对
        return keyPair;
    }

    /**
     * 获取公钥 (并进行 Base64 编码，返回一个 Base64 编码后的字符串)
     * @param keyPair：RSA 密钥对
     * @return 返回一个 Base64 编码后的公钥字符串
     */
    public static String getPublicKey(KeyPair keyPair){
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 获取私钥(并进行Base64编码，返回一个 Base64 编码后的字符串)
     * @param keyPair：RSA 密钥对
     * @return 返回一个 Base64 编码后的私钥字符串
     */
    public static String getPrivateKey(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 随机生成公钥和私钥
     */
    public static Map<String, Object> getRandomKey() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        KeyPair keyPair = getKeyPair();

        //公私钥对象存入map中
        Map<String, Object> keyMap = new HashMap(2);
        keyMap.put(PUBLIC_KEY, getPublicKey(keyPair));//获取公钥Base64编码
        keyMap.put(PRIVATE_KEY, getPrivateKey(keyPair));//获取密钥Base64编码

        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//        keyMap.put(PUBLIC_KEY, Base64Utils.encodeToString(publicKey.getEncoded()));//获取公钥Base64编码
//        keyMap.put(PRIVATE_KEY, Base64Utils.encodeToString(privateKey.getEncoded()));//获取密钥Base64编码
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

    public static String encrypt(String content, String publicKey) {
        return Base64.encodeBase64String(encrypt(content.getBytes(), getPublicKey(publicKey)));
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
    public static String content = "{\"method\":\"CELLPHONE\",\"cellphone\":\"41111111\"}";

    public static void main(String[] args) throws Exception {
        //随机生成公钥和私钥
//        Map<String, Object> map = getRandomKey();
//        System.out.println(map.get(PUBLIC_KEY).toString());
//        System.out.println(map.get(PRIVATE_KEY).toString());

//        System.out.println("排序前：" + content);
//        String jsonSorted = jsonSortToString(content, "&", "sign");
//        System.out.println("排序后：" + jsonSorted);

//        System.out.println(publicKey);
//        System.out.println(privateKey);

        String encryptedBase64 = encryptBase64(content);
        System.out.println("加密后：" + encryptedBase64);

        String decrypted = decrypt(encryptedBase64);
        System.out.println("解密后：" + decrypted);

        encryptedBase64 = "IG5KeERgArAOuHu0VheVLcZYfuE3DIrlCY18r6qCQUa3WQu4GKhVe+HGk4dx287Z+mRrkmBimsVN9kccW+xtllLdKxCwatDzKdi2Sgj+PW3geARcwBkZA6GRHyZgpFQSu4yI4aF3eKjVxGk0pbCDUM9StGFt+7AqsENe9tzhbSJ9+P2cp9rD/sxa6267UM1oexD7H7m09SRRCWUfezmDIn1OqD1ddfaHGOBRFWodnsmI4wIV6TJzzulb9JgA9ZAKObyGTLsA8oPUSDGX135TGO+qspiqFwCoT3JUiswQTRysl9F05zj97FiPLTxNjyS47gM4/b4ER958Rp5wvJuUYw==";
        System.out.println("加密后：" + encryptedBase64);

        decrypted = decrypt(encryptedBase64);
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
