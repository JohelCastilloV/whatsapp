package com.xcale.whatsapp.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xcale.whatsapp.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${app.security.expiration}")
    private  long EXPIRES_IN;
    @Value("${app.security.secret}")
    private String SECRET;
    private  Algorithm ALGORITHM;

    @PostConstruct
    public void init() {
        this.ALGORITHM = Algorithm.HMAC256(SECRET);
    }

    public  String createToken(User user) {

        return JWT.create()
                .withSubject(user.getId())
                .withClaim("name", user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRES_IN))
                .sign(ALGORITHM);
    }

    public static String unwrapBearerToken(String bearerToken) {
        String bearerPrefix = "bearer ";
        if (bearerToken.toLowerCase().startsWith(bearerPrefix)) {
            return bearerToken.substring(bearerPrefix.length());
        }

        return null;
    }

    public  DecodedJWT verifyToken(String token) {
        return JWT.require(ALGORITHM)
                .withClaimPresence("name")
                .build()
                .verify(token);
    }

    public  String getName(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim("name").asString();
    }


}
