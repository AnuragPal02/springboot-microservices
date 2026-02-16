package com.ecommerce.apigateway1.config;

import com.ecommerce.apigateway1.filter.GatewayFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GatewayConfig implements WebMvcConfigurer {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public FilterRegistrationBean<GatewayFilter> gatewayFilter(GatewayFilter filter) {
//        FilterRegistrationBean<GatewayFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(filter);
//        registrationBean.addUrlPatterns("/*");
//        return registrationBean;
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}