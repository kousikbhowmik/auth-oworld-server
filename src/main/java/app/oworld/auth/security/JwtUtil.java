package app.oworld.auth.security;


import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${spring.security.refresh-token-validity}")
    private long validity;

    private static long VALIDITY;

    @Autowired
    public KeyLoader keyLoader;

    @PostConstruct
    public void init(){
        VALIDITY = validity;
    }

    public static long getValidity(){
        return VALIDITY;
    }

    public String generateToken(String userId) throws Exception {
        PrivateKey privateKey = keyLoader.loadPrivateKey();

        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + validity))
                .signWith(privateKey)
                .compact();
    }

    public String validateToken(String token) throws Exception{
        PublicKey publicKey = keyLoader.loadPublicKey();

        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
