package com.juanjuan.service.config;

import com.juanjuan.service.web.servlet.TimeoutAnnotationHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yuhui.guan
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册一个拦截器
        // registry.addInterceptor(new TimeoutAnnotationHandlerInterceptor());
    }
}
