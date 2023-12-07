package com.billsTech.PortalSystem01.auth.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.billsTech.PortalSystem01.entity.Users;
import com.billsTech.PortalSystem01.repositories.UserRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class JwtService {
    private final UserRepo userRepo;
    @Value("${credentials.jwtSecret}")
    private String jwtSecret;

    public Users validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer("Bolu").build();
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            String email = decodedJWT.getSubject();
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(roles[0]));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,null,authorities);
            if(authenticationToken.isAuthenticated()){
                Users users = userRepo.findByEmail(email).get();
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                return users;
            }
            else {
                SecurityContextHolder.clearContext();
                authenticationToken.setAuthenticated(false);
                return null;
            }
        }
        catch (AlgorithmMismatchException ex) {
            return null;
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public String generateToken(Authentication authentication) {
    User principal = (User) authentication.getPrincipal();
    return JWT.create()
            .withSubject(principal.getUsername())
            .withExpiresAt(Date.from(Instant.now().plusMillis(84664400)))
            .withIssuer("Bolu")
            .withIssuedAt(Date.from(Instant.now()))
            .withClaim("roles", principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
            .sign(Algorithm.HMAC256(jwtSecret.getBytes()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Users user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return authorities;
    }
}

