package com.rest.fds.auth;

import com.rest.fds.service.JwtService;
import com.rest.fds.util.CachedBodyHttpServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;

    private final JwtService jwtService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    public JwtAuthenticationFilter(
            JwtService jwtService,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtService = jwtService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        logger.info("incoming request");

        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest((HttpServletRequest) request);

        try {
            String body = new String(wrappedRequest.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            logger.info("Incoming Request: Method={}, URI={}, Body={}",
                    wrappedRequest.getMethod(),
                    wrappedRequest.getRequestURI(),
                    body);
        } catch (IOException e) {
            logger.error("Failed to read request body", e);
        }

        final String authHeader = request.getHeader("Authorization");

        logger.info("incoming request autheader {} ", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.error("error auth not found ");

            filterChain.doFilter(wrappedRequest, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);

            logger.info(" trying to authenticate jwt token is : {} ",jwt);

                if (jwtService.isTokenValid(jwt)) {
                    logger.info(">>> incoming token {} ", jwtService);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            null,
                            null,
                            null
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            filterChain.doFilter(wrappedRequest, response);
                
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }

    }
}