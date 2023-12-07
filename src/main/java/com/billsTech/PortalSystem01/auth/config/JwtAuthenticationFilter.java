package com.billsTech.PortalSystem01.auth.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
    throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String email = null;
        String token = null;
     if(authorizationHeader!=null&&authorizationHeader.startsWith("Bearer ")){
         token = authorizationHeader.substring(7);
         Algorithm algorithm = Algorithm.HMAC256(jwtService.getJwtSecret().getBytes());
         JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer("Bolu").build();
         DecodedJWT decodedJWT = jwtVerifier.verify(token);
         email=decodedJWT.getSubject();
     }
     if(email!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
         UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
         if(jwtService.validateToken(token)!=null){
             var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                     userDetails,
                     null,
                     userDetails.getAuthorities()
             );
             usernamePasswordAuthenticationToken.setDetails(
                     new WebAuthenticationDetailsSource().buildDetails(request)
             );
                 SecurityContextHolder.getContext()
                         .setAuthentication(usernamePasswordAuthenticationToken);
         }
     }
     filterChain.doFilter(request,response);
}
}
