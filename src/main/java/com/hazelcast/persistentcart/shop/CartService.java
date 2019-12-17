package com.hazelcast.persistentcart.shop;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.map.AbstractEntryProcessor;
import com.hazelcast.persistentcart.authentication.User;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartService {

    private final HazelcastInstance hazelcast;

    public CartService(HazelcastInstance hazelcast) {
        this.hazelcast = hazelcast;
    }

    public void add(User user, Product product, int quantity) {
        Assert.isTrue(quantity > 0, "Quantity must be greater than 0");
        IMap<Long, List<CartRow>> cart = hazelcast.getMap("default");
        cart.executeOnKey(user.getId(), new AbstractEntryProcessor<Long, List<CartRow>>() {
            @Override
            public Object process(Map.Entry<Long, List<CartRow>> entry) {
                CartRow row = new CartRow(product);
                List<CartRow> content = entry.getValue();
                if (content == null) {
                    content = new ArrayList<>();
                }
                if (content.contains(row)) {
                    int index = content.indexOf(row);
                    CartRow existingRow = content.get(index);
                    existingRow.increaseQuantity(quantity);
                } else {
                    content.add(new CartRow(product, quantity));
                }
                entry.setValue(content);
                return null;
            }
        });
    }

    public void remove(User user, Product product) {
        IMap<Long, List<CartRow>> cart = hazelcast.getMap("default");
        cart.executeOnKey(user.getId(), new AbstractEntryProcessor<Long, List<CartRow>>() {
            @Override
            public Object process(Map.Entry<Long, List<CartRow>> entry) {
                List<CartRow> content = entry.getValue();
                content.remove(new CartRow(product));
                entry.setValue(content);
                return null;
            }
        });
    }

    public Iterable<CartRow> rows(User user) {
        IMap<Long, List<CartRow>> cart = hazelcast.getMap("default");
        return cart.getOrDefault(user.getId(), new ArrayList<>());
    }
}