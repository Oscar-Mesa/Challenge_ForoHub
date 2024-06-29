package challenge.hub.api.infra.security;

import challenge.hub.api.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.secret}")
    private String apiSecret;

    public String GenerateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("api")
                    .withSubject(user.getName())
                    .withClaim("id", user.getId())
                    .withExpiresAt(finalDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error creating JWT token", exception);
        }
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException("Token is null");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("api")
                    .build()
                    .verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid token", exception);
        }
    }

    private Instant finalDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
