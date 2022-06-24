package com.volunteer.config;

import com.volunteer.interceptor.LoginInterceptor;
import com.volunteer.interceptor.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).addPathPatterns(
                "/**"
        );
        registry.addInterceptor(new LoginInterceptor()).excludePathPatterns(
                "/user/login",
                "/topic/hot"
//                "/swagger-ui/**",
//                "/swagger-ui.html"
        );
    }
}
