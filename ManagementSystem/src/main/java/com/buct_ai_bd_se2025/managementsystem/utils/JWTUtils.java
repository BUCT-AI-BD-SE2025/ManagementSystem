package com.buct_ai_bd_se2025.managementsystem.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtils
{
    private static final String SECRET = "YOZORA";


    /**
     * 生成 JWT Token
     */
    public static String generateToken(Map<String, String> map)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);

        return generateToken(map, calendar);
    }

    public static String generateToken(Map<String, String> map, Calendar calendar)
    {
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);
        return builder.withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(SECRET));
    }
    /**
     * 解析 Token 获取用户名
     */
    public static String parseToken(String token) {
        DecodedJWT jwt = getDecodedJWT(token);
        return jwt != null ? jwt.getSubject() : null;
    }

    public static DecodedJWT getDecodedJWT(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            return verifier.verify(token);
        } catch (Exception e) {
            return null;
        }
    }
}

