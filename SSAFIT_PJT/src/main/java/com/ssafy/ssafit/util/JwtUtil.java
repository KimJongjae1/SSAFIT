package com.ssafy.ssafit.util;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "ssafitSecretKey";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 2; // 2시간

    // ✅ 토큰 생성 (숫자 ID 포함)
    public String generateToken(String userId, String nickName, int numericUserId) {
        return Jwts.builder()
                .setSubject(String.valueOf(numericUserId))
                .claim("userId", userId)               // 문자열 아이디
                .claim("nickName", nickName)
                .claim("numericUserId", numericUserId) // 실제 user_id
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // ✅ 토큰에서 클레임 추출
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("🔒 Token expired");
            return false;
        } catch (Exception e) {
            System.out.println("❌ Invalid token: " + e.getMessage());
            return false;
        }
    }

    // ✅ 숫자 user_id 반환 (기존 getUserId)
    public int getUserId(String token) {
        Claims claims = getClaims(token);
        Object value = claims.get("numericUserId");

        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            return Integer.parseInt((String) value);
        } else {
            throw new RuntimeException("numericUserId claim is missing or invalid");
        }
    }
}
