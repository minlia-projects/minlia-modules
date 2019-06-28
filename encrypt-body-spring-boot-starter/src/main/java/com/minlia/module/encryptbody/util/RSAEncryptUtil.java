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

    private static final String CHAR_SET = "UTF-8";

    public static final String KEY_ALGORITHM = "RSA";

    /**
     * RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024
     */
    public static final int KEY_SIZE = 2048;

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs02q0YdzYndJXqvr+DiFwfeQ+VSXSz3yj6vlCmvcorkKs0qIchDNd20/xY12a8hh2p8HkAt8lTL0qpC+dDahMJk8OIoeSltAHPwneUMu6EdeG5F5HNBQfuzFcFDEjZFI2mdUMsSFZqyw5HlNGF12YPNbOrR5FTiRcTRUgzlvcXM1gDDwbxpZY3rNZpoXvIwpsKMrlB+DTkn9802Qwrs07u+UCaCCqxnAQGmCiGwKbha/jQTa/1Y5aTtC9Zn+RPjvjZ+M03GZfin3u0rhLRGNJfcRsDd5zcdsZwsf8fId+TpcuHOOUvvkcLT/WEL5I5TQV0o2AR41BNW2cGOxFL+0xwIDAQAB";
    public static String PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCzTarRh3Nid0leq+v4OIXB95D5VJdLPfKPq+UKa9yiuQqzSohyEM13bT/FjXZryGHanweQC3yVMvSqkL50NqEwmTw4ih5KW0Ac/Cd5Qy7oR14bkXkc0FB+7MVwUMSNkUjaZ1QyxIVmrLDkeU0YXXZg81s6tHkVOJFxNFSDOW9xczWAMPBvGlljes1mmhe8jCmwoyuUH4NOSf3zTZDCuzTu75QJoIKrGcBAaYKIbApuFr+NBNr/VjlpO0L1mf5E+O+Nn4zTcZl+Kfe7SuEtEY0l9xGwN3nNx2xnCx/x8h35Oly4c45S++RwtP9YQvkjlNBXSjYBHjUE1bZwY7EUv7THAgMBAAECggEAQHjD3DV1Ism7owP0hDtmtRkcktp80DxFFK39XGLuYcBhfZhmOYWbK78nuBQmqZjSvraCFKRctpUs7ou/P7BJA12GDtpzC8+F3SY511t16WWIDCehwd+RoiHm2HziP/kmlgmjd+G8CfA8ZtrLAuDQaQn4GsK76wp9GZR0cv7a+JKW0LyaTDidTx8fYGsKHV4odps/erR08SBBs5rUETRXcJpGktOUe7k7YlNHCJf+Y1W5yKKehWvTh71veayhjBzxv9RqR0fVgnJDUMVEPATaTj1pVKyZgA/9oia9m8yXAnCVR6GutH0P1FisFijnAN1CpVbHeKhUy1KLZeemGG0zgQKBgQDqQRg9HBwfAHbzWE2NLRcCPIl5UOimYFeRRSBY+7DfDZFCu/jfmgh4mhrlJzDkny7dAJGtfSSM+ozppahuDB6uZjWHrbKZu2WG0qQwWojDAKsfY/TbsWlFm+TLhfALsFnSlVbTpUWqcG6/B/Jdd07ieccGFwTfLNCFdH6b64vDBwKBgQDD8rMHKQcvTEXdQTZGqJ1crTwqXuU3XMOau4h27VR7snDe4xpwl1rsP5W5SiraP5/9EtPwGKwjnEPUSWRHvj2wtBs/SB4A1VBdzKY+yM98eFzSf9HJZRra42SlU/Fw/jHKkRAUkp7gluc5DwZi7JfuxXS1RVgfMWbsTLKT8udQQQKBgQDlPUqBEu8aD5RYU0OhMkzf7WoDBICHwKQxD1q2eaf+wAI1Mko8VzqO+w/yzEV2laiAsbvd8SdBpzcatvh6qPWlaXRdEEhFVTPnml7+yronSpIrp9/I1nbUndhqquncJnngMDDF8WiZgGmAHEC74rOZwd5YQVKNLAfrcbMs1nbxJQKBgQC3SfOq8/bTiF4lq5VQnPKtuSH5ZFC265/QwjDRRgjruCuaYgbeYMXdDVFJRBY3lqJaAN2czgdfPBG6pngWH97mxmJiXFwsXVzSkNbFDeP/wzrYcFXVNCzdqS0A9Td4gV4j5HONOuVAogdhuSs5J6Sq5arY0Sev7e8fhFLaz7ENwQKBgQDjRRfpDUqxeZNjjx0Huu5EFe776ATLxhXHJT7xT7+NXKQK+SwzGMnYPK6fHlkoCOACIX0yqm2CqFNv/XT2rYJ2mFsXs4uDYV7QSDfqJ4fMyUQxsYzqQH/qBi2gP630+il7UmIA7Uddsfl6Wx8ugICX2frUTLNu6/B0a0hFiO++bw==";

    /**
     * 随机生成公钥和私钥
     */
    public static Map<String, Object> getKeyPair() throws NoSuchAlgorithmException {
        //获取密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //设置密钥长度
        keyPairGenerator.initialize(KEY_SIZE);
        //获取密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //获取公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //获取私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //公私钥对象存入map中
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
     *
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

    public static String encrypt(String content, String publicKey) {
        return Base64.encodeBase64String(encrypt(content.getBytes(), getPublicKey(publicKey)));
    }


    /**
     * 解密
     *
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

    public static String decrypt(String content, String privateKey) {
        return decrypt(content, getPrivateKey(privateKey));
    }

    public static String decrypt(String content, PrivateKey privateKey) {
        return new String(decrypt(Base64Utils.decodeFromString(content), privateKey));
    }

    public static String decryptSegment(String content, String privateKey) {
        return RSAUtils.encipher(content, privateKey, KEY_SIZE / 8 - 11);
    }

    //    public static String content = "{\"receiveTime\":\"1552988611860\",\"smsContent\":\"1411\",\"smsNumber\":\"13048989908\",\"deviceId\":\"c9a8d891add871b0\"}";
    public static String content = "{\"method\":\"CELLPHONE\",\"cellphone\":\"41111111\"}";

    public static void main(String[] args) throws Exception {
        //随机生成公钥和私钥
//        Map<String, Object> map = getKeyPair();
//        System.out.println(map.get(PUBLIC_KEY).toString());
//        System.out.println(map.get(PRIVATE_KEY).toString());

//        System.out.println("排序前：" + content);
//        String jsonSorted = jsonSortToString(content, "&", "sign");
//        System.out.println("排序后：" + jsonSorted);

//        String encryptedBase64 = encryptBase64(content);
//        System.out.println("加密后：" + encryptedBase64);
//
//        String decrypted = decrypt(encryptedBase64);
//        System.out.println("解密后：" + decrypted);

        String encryptedBase64 = RSAUtils.encipher(content, PUBLIC_KEY);
        System.out.println("加密后：" + encryptedBase64);

        String decrypted = RSAUtils.decipher(encryptedBase64, PRIVATE_KEY);
        System.out.println("解密后：" + decrypted);

        encryptedBase64 = "Hee5qd2SHdOqSRte1jAiQE2ktLXCfLRKwgXRowbUGoHamPFeHz7PvrSbW6c8hScb/5nPRhVNRPbCyCCyP4RSzvHxu+SUOr/MHsWWVH6BBPd8DvZWT78sbFeUqlvBbX15hho2xDLWUQTbNJ3qOAexRapNNnc4JeMcv+9EIOlAish91wHhDqTm/c63c1dxSDByu4VfKXOWcBnZbYJt9FB3RgCFQWkcxxEdAZndnx69aXY+jA+KIN6EIaVJZHh4dyrxiRb2Ke5DQ2C0VVGdilFqv8PpqOHpdpZrXBZk0ZLSochNDSFNZXLO8ILO9hHXiEoxBxfeNnTiVbwhCUfejlij2YBCVYa6AZFvYvXivnfw6kZz6btmEW9LbH0s0mf6nXbMrmFEKRU3FAU7qyMZouMdxwp1pQqa+bTbZP4He/5VxnrvxDYVu4PKFydRwtCfJWZs/nVQjzZkQoyRYqkLrFj2G8FNGLAPCgYOHWaChMcKnKWS5B1bTQ3gAzBA4aITLaoE4WItGI8TS7mZxtD0D+v2Q3i24SU2JEK3ZTX+jw/Jqj2XWRNKFPTTiYKN3a8HRbGvv8LfTe0cmMJrufxPmTVDAAq+Xv2bOIxNL83fZfpb9WJJ+VpUdMrkYpWyeqsBC2Maae/df2/Xv3mrzTJgfQLZ8MWtYnwwBGjkLeBR6OKYjf0nChp7/lL2gbS/3mAnVs2FWUn8ZU50qT4c0uYLt3uZ97YBFDvZ1GBrwEfJV3ocydnGtHy740sVqP/0E2zd/Cf/noX9jN+Fg6+mJ+HRNafsDJajTDFkFL6MFxIsmXx7XNvI4N/DaV4+JguOPjKDPexUo1+4+3dbr/tCmoqWOlYg3Y3dAelf9vi+BnMusnJ8KZa23Zm99Tskc5SHvx2xgk/aCkFF94QhjQYMf5T0CnOrObCJXV1Y0jF/Nfl/d7Kwn/kVMk0Ao9rIrxi4o2YhwFf3WKYvn645i+CMLGVci8F8pLnsKVyOJgD05/lkeHwyDH2dZT2A3wgy+n/eBPv5Yu8p";
        System.out.println("加密后：" + encryptedBase64);

        decrypted = RSAUtils.decipher(encryptedBase64, PRIVATE_KEY);
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
