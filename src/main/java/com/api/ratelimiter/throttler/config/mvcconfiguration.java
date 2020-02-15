package com.api.ratelimiter.throttler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class mvcconfiguration  implements WebMvcConfigurer {

    @Autowired
    throttlerInterceptor throttlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(throttlerInterceptor);
    }
}
