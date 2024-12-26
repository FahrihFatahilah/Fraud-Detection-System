package com.rest.fds.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Autowired
//    private HttpLoggingFilter loggingAspect;
//
//    @Bean
//    public FilterRegistrationBean<HttpLoggingFilter> loggingFilter() {
//        FilterRegistrationBean<HttpLoggingFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new HttpLoggingFilter());
//        registrationBean.addUrlPatterns("/api/*");  // Define URL patterns to filter
//        return registrationBean;
//    }
}