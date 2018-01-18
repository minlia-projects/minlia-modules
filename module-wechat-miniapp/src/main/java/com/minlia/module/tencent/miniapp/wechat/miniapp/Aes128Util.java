package com.minlia.module.tencent.miniapp.wechat.miniapp;

import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * @author wzw
 * @date 2017年2月16日
 */
public class Aes128Util {

  static{
		Security.addProvider(new BouncyCastleProvider());
	}	
	 //算法名  
    public static final String KEY_ALGORITHM = "AES";  
    //加解密算法/模式/填充方式  
    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";  
  
    //生成密钥  
    public static byte[] generateKey() throws Exception{  
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);  
        keyGenerator.init(128);  
        SecretKey key = keyGenerator.generateKey();  
        return key.getEncoded();  
    }  
      
    //生成iv  
    public static AlgorithmParameters generateIV(String iv) throws Exception{  
        AlgorithmParameters params = AlgorithmParameters.getInstance(KEY_ALGORITHM);  
        params.init(new IvParameterSpec(Base64.decodeBase64(iv)));  
        return params;  
    }  
      
    //转化成JAVA的密钥格式  
    public static Key convertToKey(byte[] keyBytes) throws Exception{  
  	  int base = 16;
  	  if (keyBytes.length % base != 0) {
  	   int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
  	   byte[] temp = new byte[groups * base];
  	   Arrays.fill(temp, (byte) 0);
  	   System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
  	   keyBytes = temp;
  	  }
        Key secretKey = new SecretKeySpec(keyBytes,KEY_ALGORITHM);  
        return secretKey;  
    }  
      
    //加密  
    public static byte[] encrypt(byte[] data,byte[] keyBytes,AlgorithmParameters iv) throws Exception {  
        //转化为密钥  
        Key key = convertToKey(keyBytes);  
        Security.addProvider(new BouncyCastleProvider());  
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);  
        //设置为加密模式  
        cipher.init(Cipher.ENCRYPT_MODE, key,iv);  
        return cipher.doFinal(data);  
    }  
      
    //解密  
    public static String decrypt(String encryptedData,String keyBytes,AlgorithmParameters iv) throws Exception{  
        Key key = convertToKey(Base64.decodeBase64(keyBytes));  
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);  
        //设置为解密模式  
        cipher.init(Cipher.DECRYPT_MODE, key,iv);  
        return new String(cipher.doFinal(Base64.decodeBase64(encryptedData)),"UTF-8");  
    }
    
    public static void main(String[] args) throws Exception {
//    	String encryptedData = "2my+Jxd6MWFrkRAj9jdv1IY0DINgUzV+GzTPPjh9DLzGf2XKTnrSFqNvypnjk+MWhYj5G8TrbRHoBjNdy4/fWniPbNbCf3zELfEoBwrbVwJL2AH3S2HTX4yGnjYDt3IaGnWAKyQqlPMH8r5zBvnV2lO9edEUy0uI28E3MLvcVNBeUsxWVaY5qkBnTIVG3CcX8R95f0ptm+MzDwd00gM8qS9mlOBxBXfQGBcl0gDQMMXVGCerCQ7bpbVioXMuzWo2dyS3a5SbkUVIgqcTID6SplQeemNBchQM/bLmnSPyiGrwwhvBrBusIVMrvFug99Bha4xgtAXPX+eaf/R+nTbklIE9tP2r08IWnkk2AZrOGraroq3c0aeMpvH3at9N4hitMwqHK22jtiTKKpwOdfDb/pD/eVUHtwhj4a0ZVf1E+1Q1teGEaDHzT2gB6NR0bRcZwqOKVgZF2R7xliR6oL+iFkvlJn9pJf/RqYVMlf+T5P5b5Gd/fR17L5OHKFSRlXQkIftMv0i0nD1Cd9KGJ1AB/Q==";
//   	 String keyBytes = "rptFThKxA28qxJQA/VR9GQ==";
//   	 String iv = "Oo5zbMxl/mLYiWdYHd/jKA==";
//   	 String encryptedData = "M5l7GDfHy9ss+76GAUqR5e1qdRND+0U1DKtN6+nNLDUPoryIhHRnyMQADZaVqltCqj8Meq8pq9ehmkoX5r25hTDtNOBX4+pfuNhqO0M7bkp1DM3JeBdXBtY6T6+0AVvKkkQqpp7fxwHf3XDMCmhdv3CW6gYxAcbSGX7HRsHiufMsYY9cL+nfRjw5IpQIDlLsvNPmLLPE2RwWK5D71d0gKQ==";
//   	 String keyBytes = "vnONuGhLjkwiYH6wkVtzYQ==";
//   	 String iv = "1pdS/eDf870IWm0tz8ANGw==";

//   	 String encryptedData = "0aezhh7WQsGqq1T3/usYbEr8UTGLh537STmvyKwxq5G7DWhQlFtiDmAxHoCEqI1X2KdJ2JB+coN8rvEZbnvTUNN6yKKKdQuvqZUuI1QbGIHRiq+Sshah1ddiKyUpyFRFNhRdrkM1q4W9CGXQUAUYQF1GAcgY3whO7FntO+Tynf8JgppC2obt+/wJBDqtol6tyH/VzWKICfcwwNNaKvtNNA==";
//   	 String keyBytes = "Az1z7YY3VeVDeLJ45l04Og==";
//   	 String iv = "1MZrWyxpq1Hmt6l+ssbziA==";



   	 String encryptedData = "kxD1hqzqLg+kg5561QzTyyuBYRyrQrYTK2SwIxqDRUFgWcFEM0WiQi2haEz64zYBi0YVUjqUb9RYDV6qk64Q37vO20YlDas2qP/eqEmIzOiHFrOFW6R1Wq20IFEv9b4zRVHFU7mK06NJxiMg4Wu+mxEVAOManfzOBRfdf7YxeSnG0pWzm/EYcMB82z2S7QhbGM3VtzgkUcck+YX91SggTw==";
   	 String keyBytes = "Uny1HBMEMted1yGTmBUgjw==";
   	 String iv = "lrr3l5xdXy45oR7Ygw91JQ==";

   	  String data = decrypt(encryptedData, keyBytes, generateIV(iv));


	System.out.println(data);
	}
}