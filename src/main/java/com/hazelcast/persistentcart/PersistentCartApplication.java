package com.hazelcast.persistentcart;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.persistentcart.authentication.AuthenticationFilter;
import com.hazelcast.persistentcart.authentication.UserRepository;
import com.hazelcast.persistentcart.shop.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

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
    public CartRemovalListener cartRemovalListener(HazelcastInstance hazelcast) {
        return new CartRemovalListener(hazelcast);
    }

    @Bean
    public CartRestoreService cartRestoreService(HazelcastInstance hazelcast,
                                                 CartRowRepository repository) {
        return new CartRestoreService(hazelcast, repository);
    }

    @Bean
    public EntryRemovalListener entryRemovalListener(HazelcastInstance hazelcast,
                                                     CartRowRepository cartRowRepository,
                                                     UserRepository userRepository) {
        IMap<Long, List<CartRow>> cart = hazelcast.getMap("default");
        EntryRemovalListener listener = new EntryRemovalListener(cartRowRepository, userRepository);
        cart.addEntryListener(listener, true);
        return listener;
    }

    public static void main(String[] args) {
        SpringApplication.run(PersistentCartApplication.class, args);
    }
}
