package com.hazelcast.persistentcart.shop;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.persistentcart.authentication.User;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;
import java.util.Optional;

public class CartRemovalListener implements HttpSessionListener {

    private final HazelcastInstance hazelcast;

    public CartRemovalListener(HazelcastInstance hazelcast) {
        this.hazelcast = hazelcast;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        Optional<User> user = Optional.ofNullable((User) session.getAttribute("user"));
        user.ifPresent(it -> {
            IMap<Long, List<CartRow>> cart = hazelcast.getMap("default");
            cart.remove(it.getId());
        });
    }
}
