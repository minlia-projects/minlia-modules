package com.minlia.module.pooul.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.id.UUIDGenerator;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.UUID;

/**
 * Created by garen on 2018/7/17.
 */
@Slf4j
public class JwtToken {

    private final static String public_key = "";
    private final static String private_key = "";

    private static Map<String, Object> headerClaims = Maps.newConcurrentMap();

    static {
        headerClaims.put("alg", "RS256");
        headerClaims.put("typ", "JWT");
    }

    @Deprecated
    public static String createToken(String nonceStr) {
        RSAPublicKey publicKey = RSAEncrypt.getPublicKey(public_key);
        RSAPrivateKey privateKey = RSAEncrypt.getPrivateKey(private_key);
        Algorithm algorithm = Algorithm.RSA256(publicKey,privateKey);

        String token = JWT.create()
                .withHeader(headerClaims)
                .withIssuer("auth0")
                .withClaim("pay_type","wechat.jsminipg")
                .withClaim("nonce_str", nonceStr)
                .withClaim("mch_trade_id","alextest.jsminipg.03")
                .withClaim("total_fee","1")
                .withClaim("bean","花果山 Test jsminipg")
                .withClaim("sub_appid","XXXX")
                .withClaim("sub_openid","XXXX")
                .sign(algorithm);
        return token;
    }

    public static void verifyToken(String token,String nonceStr) {
        RSAPublicKey publicKey = RSAEncrypt.getPublicKey(public_key);
        RSAPrivateKey privateKey = RSAEncrypt.getPrivateKey(private_key);
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .withClaim("pay_type","wechat.jsminipg")
                    .withClaim("nonce_str", nonceStr)
                    .withClaim("mch_trade_id","alextest.jsminipg.03")
                    .withClaim("total_fee","1")
                    .withClaim("bean","花果山 Test jsminipg")
                    .withClaim("sub_appid","XXXX")
                    .withClaim("sub_openid","XXXX")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            exception.printStackTrace();
        }
    }

//    public static void main(String[] args)  {
//        Map map = RSAEncrypt.getRandomKey();
//        System.out.println(map.get(RSAEncrypt.public_key));
//        System.out.println(map.get(RSAEncrypt.private_key));

//        String nonce_str = UUID.randomUUID().toString().replace("-","");
//        String token = createToken(nonce_str);
//        System.out.println(token);
//        verifyToken(token,nonce_str);
//    }

}
