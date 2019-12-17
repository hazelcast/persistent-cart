package com.hazelcast.persistentcart;

import com.hazelcast.persistentcart.authentication.AuthenticationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PersistentCartApplication {

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter() {
        FilterRegistrationBean<AuthenticationFilter> registration = new FilterRegistrationBean<>(
                new AuthenticationFilter()
        );
        registration.addUrlPatterns("/", "/add", "/remove");
        return registration;
    }

    public static void main(String[] args) {
        SpringApplication.run(PersistentCartApplication.class, args);
    }
}
