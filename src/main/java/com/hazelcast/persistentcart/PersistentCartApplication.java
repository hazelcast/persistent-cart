package com.hazelcast.persistentcart;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.persistentcart.authentication.AuthenticationFilter;
import com.hazelcast.persistentcart.shop.CartRemovalListener;
import com.hazelcast.persistentcart.shop.CartService;
import com.hazelcast.persistentcart.shop.CartRestoreService;
import com.hazelcast.persistentcart.shop.CartRowRepository;
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

    @Bean
    public CartService cartService(HazelcastInstance hazelcast) {
        return new CartService(hazelcast);
    }

    @Bean
    public CartRemovalListener cartRemovalListener(HazelcastInstance hazelcast,
                                                    CartRowRepository repository) {
        return new CartRemovalListener(hazelcast, repository);
    }

    @Bean
    public CartRestoreService cartRestoreService(HazelcastInstance hazelcast,
                                                 CartRowRepository repository) {
        return new CartRestoreService(hazelcast, repository);
    }

    public static void main(String[] args) {
        SpringApplication.run(PersistentCartApplication.class, args);
    }
}
