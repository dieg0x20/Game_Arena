package com.unifucamp.gamearena.security;

import java.time.*;

import com.unifucamp.gamearena.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;


@Service
public class JwtTokenService {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Value("${jwt_issuer}")
    private String ISSUER;

    @Value("${jwt.expiration.hours}")
    private Integer EXPIRATION_HOURS;

    public String generateToken(UserDetailsImpl  user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
              .withIssuer(ISSUER)
              .withIssuedAt(creationDate())
              .withExpiresAt(expirationDate())
              .withSubject(user.getUsername())
              .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token.", exception);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token invalido ou expirado.");
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(EXPIRATION_HOURS).toInstant();
    }

}