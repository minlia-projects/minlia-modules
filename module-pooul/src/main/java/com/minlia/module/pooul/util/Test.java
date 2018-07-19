package com.minlia.module.pooul.util;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.hibernate.jdbc.Expectation;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Test {

    public static final String  KEY_ALGORITHM = "RSA";
	public static final String  SIGNATURE_ALGORITHM = "MD5withRSA";
	private static final char[] bcdLookup = { 
	    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
	    'a', 'b', 'c', 'd', 'e', 'f' };
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String pubKey = "30819c300d06092a864886f70d010101050003818a0030818602818053499d537c990421af33e0d57e9b0d9e4d54d54a808b935efcee8e26460530d351600d00e16b58cb6006545cbdf23f357c5301706fb3921cf0478f62ab07fa3df8b9690aca7db9c7dc7a1a74dc9da22d19e54fb67f8e0755d31aeca392914f2c8c449c54b9aa0b5abd4cb02c851aa0ee7291b1517a46f90f1a4acc75ad1ccb4d020111";
		String priKey = "30820272020100300d06092a864886f70d01010105000482025c3082025802010002818053499d537c990421af33e0d57e9b0d9e4d54d54a808b935efcee8e26460530d351600d00e16b58cb6006545cbdf23f357c5301706fb3921cf0478f62ab07fa3df8b9690aca7db9c7dc7a1a74dc9da22d19e54fb67f8e0755d31aeca392914f2c8c449c54b9aa0b5abd4cb02c851aa0ee7291b1517a46f90f1a4acc75ad1ccb4d0201110281802731b37294fcb6a67090e24659b260c2f736faf5e22390a52bbb8e3020f3624553787e9700aafc9bf0f3eb76eff987283a816a16cb2753d162038ec50530ee3abd4777f950926aa6c727cb425d622ba91a879196bad6be7ed42e4753dd3f141e6cbc417ea057fc229eda6c7c0f5ced8c4599eca4be085ec692322bcd231b26f1024100a64661969275f8d83cffaaa7cb320e8d3f18a304adf47163a80012666c8401e5f132dd33e3fb5778e0490e632c3de67cc9d4fe51eee581cd6df4081327eb1851024100803b28826cd09e4d3845dfe00afaf6d8826c975184914124a83882aaefe74285f401b3f3c0f3bc184b737b41b83741a794d59c21778faddbb5ac274e9a98003d024061cf0c3a74456533e7a57371c2d226ad7068d85d1b0842b31787925a5df34c69247845e249df246538a371dffbe82d3a589b686c6e68e2f14fbcb974ae11d21102402d420e4c2667bf668c54e59a5e3a753d5b3562953dd8e9d0b3d7b5a5be1562c5dda63f83350abadb65ec85daf5b9263b257891753941c4e422008657fa53c3d9024044471c1df5e608c7ea111d621b900aa742210c10c43cf09649fa6c9a5048cabf832b96690dbbe68d983ac3abb08189add99c48583b9c0ead018f91335c20ee86";
		
		String plainTextString = "nwL56uf%2CThrpByhgk6fEUlCkx784OfS2YgF3son%2FG1YS2FKAURJ4Wg%3D%3D"; // Ê±¼ä´Á¼ÓÃÜ´®

		//加密
		String sign = "";
		try {
			sign = sign(plainTextString, priKey);
			System.out.println("SignData:" + sign);
			
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			System.out.println("SignData Exception Error!");
		}

		//认证
		try {
			System.out.println("CheckSignResult: " + verify(plainTextString, pubKey, sign));
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Verify Exception Error!");
		}

		//解密
		try {
			String code = decrypt(plainTextString,pubKey);
			System.out.println(code);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}	
	
	/*
	 * 
	 * ÓÃË½Ô¿¶ÔÐÅÏ¢Éú³ÉÊý×ÖÇ©Ãû
	 * 
	 * @param data  ´ýÇ©ÃûÐÅÏ¢
	 * @param priKey Ë½Ô¿
	 * 
	 */
	
	public static String sign(String data, String priKey) throws Exception{
		byte [] keyBytes = hexStrToBytes(priKey.trim());
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		
		signature.initSign(privateK);
		signature.update(data.getBytes("UTF8"));
		
		return bytesToHexStr(signature.sign());
	}

	public static String decrypt(String data,String pubKey) throws Exception{
		byte [] keyBytes = hexStrToBytes(pubKey.trim());
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);

		byte[] cipherText = null;
		Cipher cipher;
		try {
//			cipher = Cipher.getInstance("RSA");
			cipher = Cipher.getInstance(SIGNATURE_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			cipherText = cipher.doFinal(data.getBytes("UTF8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytesToHexStr(cipherText);
	}
	
	/*
	 * 
	 * Ð£ÑéÊý×ÖÇ©Ãû
	 * 
	 * @param data  ÒÑ¼ÓÃÜÊý¾Ý
	 * @param pubKey ¹«Ô¿
	 * @param sign  Êý×ÖÇ©ÃûÐÅÏ¢
	 * 
	 */
	public static boolean verify(String data, String pubKey, String sign) throws Exception{
		byte [] keyBytes = hexStrToBytes(pubKey.trim());
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		
		signature.initVerify(publicKey);
		signature.update(data.getBytes("UTF8"));
		
		return signature.verify(hexStrToBytes(sign));
	}
	
	public static final byte[] hexStrToBytes(String s)
	  {
	    byte[] abyte0 = new byte[s.length() / 2];
	    for (int i = 0; i < abyte0.length; ++i) {
	      abyte0[i] = (byte)Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
	    }
	    return abyte0;
	  }
	
	public static final String bytesToHexStr(byte[] abyte0)
	  {
	    StringBuffer stringbuffer = new StringBuffer(abyte0.length * 2);
	    for (int i = 0; i < abyte0.length; ++i)
	    {
	      stringbuffer.append(bcdLookup[(abyte0[i] >>> 4 & 0xF)]);
	      stringbuffer.append(bcdLookup[(abyte0[i] & 0xF)]);
	    }

	    return stringbuffer.toString();
	  }
}








