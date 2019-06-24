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
    public static String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs02q0YdzYndJXqvr+DiFwfeQ+VSXSz3yj6vlCmvcorkKs0qIchDNd20/xY12a8hh2p8HkAt8lTL0qpC+dDahMJk8OIoeSltAHPwneUMu6EdeG5F5HNBQfuzFcFDEjZFI2mdUMsSFZqyw5HlNGF12YPNbOrR5FTiRcTRUgzlvcXM1gDDwbxpZY3rNZpoXvIwpsKMrlB+DTkn9802Qwrs07u+UCaCCqxnAQGmCiGwKbha/jQTa/1Y5aTtC9Zn+RPjvjZ+M03GZfin3u0rhLRGNJfcRsDd5zcdsZwsf8fId+TpcuHOOUvvkcLT/WEL5I5TQV0o2AR41BNW2cGOxFL+0xwIDAQAB";
    public static String PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCzTarRh3Nid0leq+v4OIXB95D5VJdLPfKPq+UKa9yiuQqzSohyEM13bT/FjXZryGHanweQC3yVMvSqkL50NqEwmTw4ih5KW0Ac/Cd5Qy7oR14bkXkc0FB+7MVwUMSNkUjaZ1QyxIVmrLDkeU0YXXZg81s6tHkVOJFxNFSDOW9xczWAMPBvGlljes1mmhe8jCmwoyuUH4NOSf3zTZDCuzTu75QJoIKrGcBAaYKIbApuFr+NBNr/VjlpO0L1mf5E+O+Nn4zTcZl+Kfe7SuEtEY0l9xGwN3nNx2xnCx/x8h35Oly4c45S++RwtP9YQvkjlNBXSjYBHjUE1bZwY7EUv7THAgMBAAECggEAQHjD3DV1Ism7owP0hDtmtRkcktp80DxFFK39XGLuYcBhfZhmOYWbK78nuBQmqZjSvraCFKRctpUs7ou/P7BJA12GDtpzC8+F3SY511t16WWIDCehwd+RoiHm2HziP/kmlgmjd+G8CfA8ZtrLAuDQaQn4GsK76wp9GZR0cv7a+JKW0LyaTDidTx8fYGsKHV4odps/erR08SBBs5rUETRXcJpGktOUe7k7YlNHCJf+Y1W5yKKehWvTh71veayhjBzxv9RqR0fVgnJDUMVEPATaTj1pVKyZgA/9oia9m8yXAnCVR6GutH0P1FisFijnAN1CpVbHeKhUy1KLZeemGG0zgQKBgQDqQRg9HBwfAHbzWE2NLRcCPIl5UOimYFeRRSBY+7DfDZFCu/jfmgh4mhrlJzDkny7dAJGtfSSM+ozppahuDB6uZjWHrbKZu2WG0qQwWojDAKsfY/TbsWlFm+TLhfALsFnSlVbTpUWqcG6/B/Jdd07ieccGFwTfLNCFdH6b64vDBwKBgQDD8rMHKQcvTEXdQTZGqJ1crTwqXuU3XMOau4h27VR7snDe4xpwl1rsP5W5SiraP5/9EtPwGKwjnEPUSWRHvj2wtBs/SB4A1VBdzKY+yM98eFzSf9HJZRra42SlU/Fw/jHKkRAUkp7gluc5DwZi7JfuxXS1RVgfMWbsTLKT8udQQQKBgQDlPUqBEu8aD5RYU0OhMkzf7WoDBICHwKQxD1q2eaf+wAI1Mko8VzqO+w/yzEV2laiAsbvd8SdBpzcatvh6qPWlaXRdEEhFVTPnml7+yronSpIrp9/I1nbUndhqquncJnngMDDF8WiZgGmAHEC74rOZwd5YQVKNLAfrcbMs1nbxJQKBgQC3SfOq8/bTiF4lq5VQnPKtuSH5ZFC265/QwjDRRgjruCuaYgbeYMXdDVFJRBY3lqJaAN2czgdfPBG6pngWH97mxmJiXFwsXVzSkNbFDeP/wzrYcFXVNCzdqS0A9Td4gV4j5HONOuVAogdhuSs5J6Sq5arY0Sev7e8fhFLaz7ENwQKBgQDjRRfpDUqxeZNjjx0Huu5EFe776ATLxhXHJT7xT7+NXKQK+SwzGMnYPK6fHlkoCOACIX0yqm2CqFNv/XT2rYJ2mFsXs4uDYV7QSDfqJ4fMyUQxsYzqQH/qBi2gP630+il7UmIA7Uddsfl6Wx8ugICX2frUTLNu6/B0a0hFiO++bw==";

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

//        String encryptedBase64 = encryptBase64(content);
//        System.out.println("加密后：" + encryptedBase64);
//
//        String decrypted = decrypt(encryptedBase64);
//        System.out.println("解密后：" + decrypted);
//
//        encryptedBase64 = "hPgA7MDO3PID1W18Q4Sf5VdD7n7qKnC2UfaSqkvWL64gwspP0zsJxByaBxNxTCy8FYrmvhT8/uaOWgXNzqbJnCVuhEeKQLv5jCSlj0f9wkVJ+L+rqJezfta7qlOx5BIl2m8vI2tJe/5R278QZ+FybUdYMflLR8Hw0+tYBKugxgMhF+CEumLZoR/h8764HmNDJSFMUppVSe/uBpSruei3cpbbfUy31MomY4OuTSwRYRMsrEjkyXIXQ6LUvzjJKzYs/jNPVwVGm5Pt3ZsLhrZb8keNm6RXxLincDPI7OKA6o7elKNDSSajVTXtyjGi30T06GMvxlCLJbYD5TwnC7DufA==";
//        System.out.println("加密后：" + encryptedBase64);
//
//        decrypted = decrypt(encryptedBase64, PRIVATE_KEY);
//        System.out.println("解密后：" + decrypted);
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
