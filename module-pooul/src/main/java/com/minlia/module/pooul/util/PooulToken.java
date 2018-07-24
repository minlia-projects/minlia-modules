package com.minlia.module.pooul.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.pooul.config.PooulProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * Created by garen on 2018/7/17.
 */
@Slf4j
public class PooulToken {

    private static PooulProperties pooulProperties;

    private static Map<String, Object> headerClaims = Maps.newConcurrentMap();
    static {
        headerClaims.put("alg", "RS256");
        headerClaims.put("typ", "JWT");
    }

    private static Algorithm algorithm = null;
    public static Algorithm getAlgorithm(){
        if (null == algorithm){
            pooulProperties = ContextHolder.getContext().getBean(PooulProperties.class);
            RSAPublicKey publicKey = RSAEncrypt.getPublicKey(pooulProperties.getPooulPublicKey());
            RSAPrivateKey privateKey = RSAEncrypt.getPrivateKey(pooulProperties.getPrivateKey());
            algorithm = Algorithm.RSA256(publicKey,privateKey);
        }
        return algorithm;
    }

    /**
     * 创建Token
     * @param map
     * @return
     */
    public static String create(Map<String,Object> map) {
        JWTCreator.Builder builder = JWT.create();
        builder.withHeader(headerClaims);

        for (Map.Entry<String, Object> entry: map.entrySet()) {
            if (null != entry.getValue() && !entry.getKey().equals("class")) {
                builder.withClaim(entry.getKey(),entry.getValue().toString());
            }
        }
        return builder.sign(getAlgorithm());
    }

    /**
     * 验证token
     * @param token
     */
    public static boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(getAlgorithm()).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return true;
        } catch (JWTVerificationException e){
            //Invalid signature/claims
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 验证token并返回参数对象
     * @param token
     * @param beanClass
     * @return
     */
    public static Object verify(String token, Class beanClass) {
        JWTVerifier verifier = JWT.require(getAlgorithm()).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        Map<String,Claim> claims = decodedJWT.getClaims();
        Map<String,Object> resultMap = Maps.newHashMap();
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            resultMap.put(entry.getKey(),entry.getValue().asString());
            System.out.println(entry.getKey() + ":" +entry.getValue().asString());
        }
        Object object = null;
        try {
            object = beanClass.newInstance();
            BeanUtils.populate(object,resultMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 获取claims
     * @param token
     * @return
     */
    public static Map<String,Claim> getClaims(String token) {
        JWTVerifier verifier = JWT.require(getAlgorithm()).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        Map<String,Claim> claims = decodedJWT.getClaims();
        return claims;
    }

    @Deprecated
    public static Object decode(String token) throws Exception {
        Map<String,Object> resultMap = Maps.newHashMap();
        Map<String,Claim> claims = JWT.decode(token).getClaims();
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            resultMap.put(entry.getKey(),entry.getValue());
        }
        return resultMap;
    }

    public static void main(String[] args)  {
////        Map map = RSAEncrypt.getRandomKey();
////        System.out.println(map.get(RSAEncrypt.public_key));
////        System.out.println(map.get(RSAEncrypt.private_key));
//        String nonce_str = UUID.randomUUID().toString().replace("-","");
//        String token = createToken(nonce_str);
//        System.out.println(token);
//        verifyToken(token,nonce_str);


//        try {
//            PooulPayNotifyResponseBody body = (PooulPayNotifyResponseBody) decoded(token, PooulPayNotifyResponseBody.class);
//            System.out.println(body.getAppId());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
