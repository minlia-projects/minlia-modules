package com.minlia.module.pooul.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

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
//            byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKeyData); //将字符串Base64解码
//            byte[] keyBytes = Base64.decode(publicKeyData);
            byte[] keyBytes = Base64Utils.decodeFromString(publicKeyData); //将字符串Base64解码
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
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
//            byte[] keyBytes = Base64.decode(privateKeyData); //将字符串Base64解码
//            byte[] keyBytes = new BASE64Decoder().decodeBuffer(privateKeyData); //将字符串Base64解码
//            byte[] keyBytes = Base64.getDecoder().decode(privateKeyData);
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

    /**
     * 加密
     */
    public static byte[] encrypt(String data, Key key) {
        try {
            //取公钥
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     */
    public static byte[] decrypt(byte[] data, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String input) {
        byte[] cipherText = null;
        Cipher cipher;
        RSAPublicKey publicKey = (RSAPublicKey) getPublicKey(pubKey);
        RSAPrivateKey privateKey = (RSAPrivateKey) getPrivateKey(priKey);
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipherText = cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64Utils.encodeToString(cipherText);
    }

    static String pubKey = "30819c300d06092a864886f70d010101050003818a0030818602818053499d537c990421af33e0d57e9b0d9e4d54d54a808b935efcee8e26460530d351600d00e16b58cb6006545cbdf23f357c5301706fb3921cf0478f62ab07fa3df8b9690aca7db9c7dc7a1a74dc9da22d19e54fb67f8e0755d31aeca392914f2c8c449c54b9aa0b5abd4cb02c851aa0ee7291b1517a46f90f1a4acc75ad1ccb4d020111";
    static String priKey = "30820272020100300d06092a864886f70d01010105000482025c3082025802010002818053499d537c990421af33e0d57e9b0d9e4d54d54a808b935efcee8e26460530d351600d00e16b58cb6006545cbdf23f357c5301706fb3921cf0478f62ab07fa3df8b9690aca7db9c7dc7a1a74dc9da22d19e54fb67f8e0755d31aeca392914f2c8c449c54b9aa0b5abd4cb02c851aa0ee7291b1517a46f90f1a4acc75ad1ccb4d0201110281802731b37294fcb6a67090e24659b260c2f736faf5e22390a52bbb8e3020f3624553787e9700aafc9bf0f3eb76eff987283a816a16cb2753d162038ec50530ee3abd4777f950926aa6c727cb425d622ba91a879196bad6be7ed42e4753dd3f141e6cbc417ea057fc229eda6c7c0f5ced8c4599eca4be085ec692322bcd231b26f1024100a64661969275f8d83cffaaa7cb320e8d3f18a304adf47163a80012666c8401e5f132dd33e3fb5778e0490e632c3de67cc9d4fe51eee581cd6df4081327eb1851024100803b28826cd09e4d3845dfe00afaf6d8826c975184914124a83882aaefe74285f401b3f3c0f3bc184b737b41b83741a794d59c21778faddbb5ac274e9a98003d024061cf0c3a74456533e7a57371c2d226ad7068d85d1b0842b31787925a5df34c69247845e249df246538a371dffbe82d3a589b686c6e68e2f14fbcb974ae11d21102402d420e4c2667bf668c54e59a5e3a753d5b3562953dd8e9d0b3d7b5a5be1562c5dda63f83350abadb65ec85daf5b9263b257891753941c4e422008657fa53c3d9024044471c1df5e608c7ea111d621b900aa742210c10c43cf09649fa6c9a5048cabf832b96690dbbe68d983ac3abb08189add99c48583b9c0ead018f91335c20ee86";
    static String plainTextString = "nwL56uf%2CThrpByhgk6fEUlCkx784OfS2YgF3son%2FG1YS2FKAURJ4Wg%3D%3D";

    public static void main(String[] args)  {
//        String xx = decrypt(plainTextString);
//        System.out.println(xx);

        //随机生成公钥和私钥
        Map<String, Object> map = getRandomKey();
        System.out.println(map.get(public_key).toString());
        System.out.println(map.get(private_key).toString());
    }

}
