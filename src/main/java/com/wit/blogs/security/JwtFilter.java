package com.wit.blogs.security;

import com.wit.blogs.config.JwtSecretProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtSecretProperties jwtSecretProperties;

    public JwtFilter(JwtSecretProperties jwtSecretProperties) {
        this.jwtSecretProperties = jwtSecretProperties;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader !=null && authHeader.startsWith("Bearer ")) {
            var token = authHeader.substring(7);
            try {
                SecretKey key = Keys.hmacShaKeyFor(jwtSecretProperties.getAccessTokenSecret().getBytes());
                var jwt = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
                var rolesClaimString = jwt.getPayload().get("roles");
                var userId = jwt.getPayload().get("userId");
                var authorities = new ArrayList<SimpleGrantedAuthority>();
                if (rolesClaimString != null) {
                    authorities = (ArrayList<SimpleGrantedAuthority>) Arrays.stream(rolesClaimString.toString().split(";"))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                }
                JwtUser jwtUser = new JwtUser(jwt.getPayload().getSubject(), Long.valueOf(Optional.ofNullable(userId).map(id->id.toString()).orElse(null)), jwt.getPayload(), authorities);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(jwtUser, null, authorities);
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authToken);
                SecurityContextHolder.setContext(securityContext);
            } catch (Exception e) {
                log.info("Error passing jwt authentication token {}", e);
            }

        }
        filterChain.doFilter(request, response);
    }
}
