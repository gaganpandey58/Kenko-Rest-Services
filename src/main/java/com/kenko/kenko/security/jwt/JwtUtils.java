package com.kenko.kenko.security.jwt;

import com.kenko.kenko.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrinciple = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrinciple.getEmail()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException signatureException) {
            throw new RuntimeException("Invalid JWt signature: " + signatureException.getMessage(), signatureException.getCause());
        } catch (MalformedJwtException malformedJwtException) {
            throw new RuntimeException("Invalid JWT token: " + malformedJwtException.getMessage(), malformedJwtException.getCause());
        } catch (ExpiredJwtException expiredJwtException) {
            throw new RuntimeException("JWT token is expired: " + expiredJwtException.getMessage(), expiredJwtException.getCause());
        } catch (UnsupportedJwtException unsupportedJwtException) {
            throw new RuntimeException("JWT token is unsupported: " + unsupportedJwtException.getMessage(), unsupportedJwtException.getCause());
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new RuntimeException("JWT claims string is empty: " + illegalArgumentException.getMessage(), illegalArgumentException.getCause());
        }
    }
}
