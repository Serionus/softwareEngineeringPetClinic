package com.io.petclinic.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_AUTHORIZATION_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        /* retrieve header */
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(TOKEN_AUTHORIZATION_PREFIX)) {

            /* retrieve token */
            String token = header.replace(TOKEN_AUTHORIZATION_PREFIX, "");

            try {
                /* verify token */
                String username = JWT.require(Algorithm.HMAC256(loadSecret())).build().verify(token)
                        .getSubject();

                /* create authentication */
                SecurityContextHolder.getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(username, null,
                                Collections.emptyList()));
            } catch (JWTVerificationException verificationException) {
                /* do nothing, user will be not authenticated */
            }

        }
        filterChain.doFilter(request, response);
    }

    private String loadSecret() throws IOException {
        Properties properties = new Properties();
        properties.load(new ClassPathResource("application.properties").getInputStream());
        return properties.getProperty("jwt.secret");
    }
}
