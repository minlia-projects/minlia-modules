package com.minlia.module.pooul.util;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * Created by garen on 2018/7/18.
 */
@Slf4j
public class RSAEncrypt {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String public_key = "public_key";
    public static final String private_key = "private_key";

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
//        String publicKeyBase64 = new BASE64Encoder().encode(publicKeyBytes);
//        String privateKeyBase64 = new BASE64Encoder().encode(privateKeyBytes);
//
//        String publicKeyBase641 = Base64Utils.encodeToString(publicKey.getEncoded());
//        String privateKeyBase641 = Base64Utils.encodeToString(privateKey.getEncoded());

        Map<String, Object> keyMap = new HashMap(2);
        keyMap.put(public_key, Base64Utils.encodeToString(publicKey.getEncoded()));//获取公钥Base64编码
        keyMap.put(private_key, Base64Utils.encodeToString(privateKey.getEncoded()));//获取密钥Base64编码
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
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
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
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");//指定RSA
            privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);//生成私钥
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static byte[] encrypt(byte[] content, PrivateKey privateKey) throws Exception{
        Cipher cipher=Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }

    public static byte[] encrypt(String content) throws Exception {
        return encrypt(content.getBytes(), getPrivateKey(priKey));
    }

    public static String encryptBase64(String content) throws Exception {
        return Base64Utils.encodeToString(encrypt(content.getBytes(), getPrivateKey(priKey)));
    }

    public static byte[] decrypt(byte[] content, PublicKey publicKey) throws Exception{
        Cipher cipher=Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    public static String decrypt(String content) throws Exception {
        return new String(decrypt(Base64Utils.decodeFromString(content), getPublicKey(pubKey)));
    }

    public static String content="{\"receiveTime\":\"1552988611860\",\"smsContent\":\"1411\",\"smsNumber\":\"13048989908\",\"deviceId\":\"c9a8d891add871b0\"}";
    public static String pubKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCISLP98M/56HexX/9FDM8iuIEQozy6kn2JMcbZS5/BhJ+U4PZIChJfggYlWnd8NWn4BYr2kxxyO8Qgvc8rpRZCkN0OSLqLgZGmNvoSlDw80UXq90ZsVHDTOHuSFHw8Bv//B4evUNJBB8g9tpVxr6P5EJ6FMoR/kY2dVFQCQM4+5QIDAQAB";
    public static String priKey="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIhIs/3wz/nod7Ff/0UMzyK4gRCjPLqSfYkxxtlLn8GEn5Tg9kgKEl+CBiVad3w1afgFivaTHHI7xCC9zyulFkKQ3Q5IuouBkaY2+hKUPDzRRer3RmxUcNM4e5IUfDwG//8Hh69Q0kEHyD22lXGvo/kQnoUyhH+RjZ1UVAJAzj7lAgMBAAECgYAVh26vsggY0Yl/Asw/qztZn837w93HF3cvYiaokxLErl/LVBJz5OtsHQ09f2IaxBFedfmy5CB9R0W/aly851JxrI8WAkx2W2FNllzhha01fmlNlOSumoiRF++JcbsAjDcrcIiR8eSVNuB6ymBCrx/FqhdX3+t/VUbSAFXYT9tsgQJBALsHurnovZS1qjCTl6pkNS0V5qio88SzYP7lzgq0eYGlvfupdlLX8/MrSdi4DherMTcutUcaTzgQU20uAI0EMyECQQC6il1Kdkw8Peeb0JZMHbs+cMCsbGATiAt4pfo1b/i9/BO0QnRgDqYcjt3J9Ux22dPYbDpDtMjMRNrAKFb4BJdFAkBMrdWTZOVc88IL2mcC98SJcII5wdL3YSeyOZto7icmzUH/zLFzM5CTsLq8/HDiqVArNJ4jwZia/q6Fg6e8KO2hAkB0EK1VLF/ox7e5GkK533Hmuu8XGWN6I5bHnbYd06qYQyTbbtHMBrFSaY4UH91Qwd3u9gAWqoCZoGnfT/o03V5lAkBqq8jZd2lHifey+9cf1hsHD5WQbjJKPPIb57CK08hn7vUlX5ePJ02Q8AhdZKETaW+EsqJWpNgsu5wPqsy2UynO";

    public static void main(String[] args) throws Exception {
//        //随机生成公钥和私钥
//        Map<String, Object> map = getRandomKey();
//        System.out.println(map.get(public_key).toString());
//        System.out.println(map.get(private_key).toString());

        System.out.println("排序前：" + content);
        String jsonSorted = jsonSortToString(content, "&", "sign");
        System.out.println("排序后：" + jsonSorted);

        String encryptedBase64 = encryptBase64(content);
        System.out.println("加密后：" + encryptedBase64);

        String decrypted = decrypt(encryptedBase64);
        System.out.println("解密后：" + decrypted);
    }

    public static String jsonSortToString(String jsonStr, String joiner, String... ignores) {
        Gson gson = new Gson();
        Map<String, Integer> map = gson.fromJson(jsonStr,Map.class);
//        map.keySet().forEach(System.out::println);
        StringJoiner sj = new StringJoiner(joiner);
        List list = Arrays.asList(ignores);
        map.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(x -> {
            if (CollectionUtils.isNotEmpty(list) && !list.contains(x.getKey())) {
                sj.add(x.toString());
            }
        });
        return sj.toString();
    }

}
