//package com.vrpigroup.usermodule.security;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.http.HttpServletRequest;
//import java.security.Key;
//import static com.vrpigroup.usermodule.constants.UserConstants.SECRET_KEY;
//
//public record JwtTokenProvider() {
//
//    /*public static void validateToken(HttpServletRequest request) {
//        String token = extractToken(request);
//        if (token != null) {
//            try {
//                Claims claims = Jwts.parserBuilder()
//                        .setSigningKey(getKey())
//                        .build()
//                        .parseClaimsJws(token)
//                        .getBody();
//            } catch (Exception e) {
//                throw new RuntimeException("Invalid token");
//            }
//        }
//    }
//
//    private static String extractToken(HttpServletRequest request) {
//        String authorizationHeader = request.getHeader("Authorization");
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            return authorizationHeader.substring(7);
//        }
//        return null;
//    }
//
//    private static Key getKey() {
//        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
//    }*/
//}