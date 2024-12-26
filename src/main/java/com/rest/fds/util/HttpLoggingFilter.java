package com.rest.fds.util;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@Component
public class HttpLoggingFilter implements jakarta.servlet.Filter {
    private static final Logger logger = LoggerFactory.getLogger(HttpLoggingFilter.class);


    @Override
    public void init(jakarta.servlet.FilterConfig filterConfig) throws jakarta.servlet.ServletException {

        jakarta.servlet.Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(jakarta.servlet.ServletRequest servletRequest, jakarta.servlet.ServletResponse servletResponse, jakarta.servlet.FilterChain filterChain) throws IOException, jakarta.servlet.ServletException {
//        if (servletRequest instanceof HttpServletRequest) {
//            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
//
//            // Wrap the request to allow multiple reads of the body
//            CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(httpRequest);
//
//            // Log request details
//            logger.info("Incoming Request: Method={}, URI={}, Body={}",
//                    wrappedRequest.getMethod(),
//                    wrappedRequest.getRequestURI(),
//                    new String(wrappedRequest.getInputStream().readAllBytes()));
//
//            // Proceed with the filter chain using the wrapped request
//            filterChain.doFilter(servletRequest, servletResponse);
//        } else {
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
    }

    @Override
    public void destroy() {
        jakarta.servlet.Filter.super.destroy();
    }
}
