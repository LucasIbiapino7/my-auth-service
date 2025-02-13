package com.cosmo.my_auth_service.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cosmo.my_auth_service.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user){
        try {
            String[] authorities = new String[user.getAuthorities().size()];
            int cont = 0;
            for (GrantedAuthority authority : user.getAuthorities()) {
                authorities[cont] = authority.getAuthority();
            }
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("my-auth-service") // quem criou o token (aplicação)
                    .withSubject(user.getEmail()) // Usuário que está recebendo esse token, ou seja, quem está fazendo login
                    .withExpiresAt(generateExpirationDate()) // Tempo de experição do token
                    .withArrayClaim("roles", authorities)
                    .sign(algorithm); // assinatura (algoritmo de criptografia)
            return token;
        }catch (JWTCreationException exception){
            throw new RuntimeException("error while generating token");
        }
    }

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("my-auth-service")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException exception){
            return "";
        }
    }


    /**
     *
     * @return Instant adicionado duas horas do momento atual
     */
    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
