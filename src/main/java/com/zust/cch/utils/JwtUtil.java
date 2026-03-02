package com.zust.cch.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zust.cch.entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String KEY = "yzdy_s"; // 密钥
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 保质期

    // 生成token
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("user", claims) // 添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign(Algorithm.HMAC256(KEY));
    }

    // 验证token并返回数据
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("user")
                .asMap();
    }

    // 从 user 到 token
    public static String genToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("userName", user.getUserName());
        claims.put("codeforcesName", user.getCodeforcesName());
        claims.put("mail", user.getMail());
        return genToken(claims);
    }
}