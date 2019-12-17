package com.hazelcast.persistentcart.shop;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.persistentcart.authentication.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class CartRestoreService {

    private final HazelcastInstance hazelcast;
    private final CartRowRepository repository;

    public CartRestoreService(HazelcastInstance hazelcast, CartRowRepository repository) {
        this.hazelcast = hazelcast;
        this.repository = repository;
    }

    @Transactional
    public void restore(User user) {
        IMap<Long, List<CartRow>> cart = hazelcast.getMap("default");
        List<CartRow> rows = repository.findAllByUser(user);
        cart.put(user.getId(), new ArrayList<>(rows));
        repository.deleteAllByUser(user);
    }
}
