package com.minlia.modules.security.authentication.jwt;

import com.minlia.cloud.utils.LocalDateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.util.*;

public class JWT {

    /**
     * 利用jwt生成token信息.
     *
     * @param claims 数据声明（Claim）其实就是一个Map，比如我们想放入用户名，
     *               可以简单的创建一个Map然后put进去
     * @param secret 用于进行签名的秘钥
     * @return
     * @throws Exception
     */
    public static String generateToken(Map<String, Object> claims, String secret, Integer tokenExpirationTime) throws Exception {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(LocalDateUtils.localDateTimeToDate(LocalDateTime.now().plusMinutes(tokenExpirationTime)))
                .signWith(SignatureAlgorithm.HS512, secret) //采用什么算法是可以自己选择的，不一定非要采用HS512
                .compact();
    }

    /**
     * 利用jwt解析token信息.
     *
     * @param token  要解析的token信息
     * @param secret 用于进行签名的秘钥
     * @return
     * @throws Exception
     */
    public static Optional<Claims> getClaimsFromToken(String token, String secret) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            return Optional.of(claims);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * 验证token是否过期
     *
     * @param tooken 要解析的token信息
     * @param secret 用于进行签名的秘钥
     * @return
     * @throws Exception
     */
    public static boolean isExpired(String tooken, String secret) throws Exception {
        Optional<Claims> claims = getClaimsFromToken(tooken, secret);
        if (claims.isPresent()) {
            Date expiration = claims.get().getExpiration();
            return expiration.before(expiration);
        }
        return false;
    }

    /**
     * 获取tooken中的参数值
     *
     * @param token  要解析的token信息
     * @param secret 用于进行签名的秘钥
     * @return
     * @throws Exception
     */
    public static Map<String, Object> extractInfo(String token, String secret) throws Exception {
        Optional<Claims> claims = getClaimsFromToken(token, secret);
        if (claims.isPresent()) {
            Map<String, Object> info = new HashMap<String, Object>();
            Set<String> keySet = claims.get().keySet();
            //通过迭代，提取token中的参数信息
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object value = claims.get().get(key);
                info.put(key, value);

            }
            return info;
        }
        return null;
    }

}