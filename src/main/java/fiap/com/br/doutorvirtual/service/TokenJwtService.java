package fiap.com.br.doutorvirtual.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import fiap.com.br.doutorvirtual.models.Credencial;
import fiap.com.br.doutorvirtual.models.JwtToken;
import fiap.com.br.doutorvirtual.models.Usuario;
import fiap.com.br.doutorvirtual.repositories.UsuarioRepository;

@Service
public class TokenJwtService {

    private String secret = "meusecret";

    @Autowired
    UsuarioRepository repository;

    public JwtToken generateToken(Credencial credencial) {
        Algorithm alg = Algorithm.HMAC256(secret);
        var token = JWT.create()
                    .withExpiresAt(Instant.now().plus(2, ChronoUnit.HOURS))
                    .withSubject(credencial.email())
                    .withIssuer("Doutorvirtual")
                    .sign(alg)
                    ;

        return new JwtToken(token);
    }

    public Usuario validate(String token) {
        Algorithm alg = Algorithm.HMAC256(secret);
        var email = JWT.require(alg)
                    .withIssuer("Doutorvirtual")
                    .build()
                    .verify(token)
                    .getSubject();
        
        return repository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Token inválido"));
    }


    
}