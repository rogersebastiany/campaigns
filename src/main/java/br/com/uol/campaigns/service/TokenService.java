package br.com.uol.campaigns.service;

import br.com.uol.campaigns.user.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

//    public String generateToken(UserModel userModel) {
//        try {
//            var algorithm = Algorithm.HMAC256(secret);
//            return JWT.create()
//                    .withIssuer("Campaigns API")
//                    .withSubject(userModel.getUsername())
//                    .withExpiresAt(getExpirationDate())
//                    .sign(algorithm);
//        } catch (JWTCreationException exception){
//            throw new RuntimeException("Error generating JWT", exception);
//        }
//    }

    public String generateToken(UserModel userModel) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth0")
                    .withSubject(userModel.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error generating JWT", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException("Invalid or expired JWT");
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}