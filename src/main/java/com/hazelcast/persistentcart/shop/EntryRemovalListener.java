package com.hazelcast.persistentcart.shop;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.map.listener.EntryRemovedListener;
import com.hazelcast.persistentcart.authentication.User;
import com.hazelcast.persistentcart.authentication.UserRepository;

import java.util.List;
import java.util.Optional;

public class EntryRemovalListener implements EntryRemovedListener<Long, List<CartRow>> {

    private final CartRowRepository cartRowrepository;
    private final UserRepository userRepository;

    public EntryRemovalListener(CartRowRepository cartRowrepository, UserRepository userRepository) {
        this.cartRowrepository = cartRowrepository;
        this.userRepository = userRepository;
    }

    @Override
    public void entryRemoved(EntryEvent<Long, List<CartRow>> event) {
        Optional<User> user = userRepository.findById(event.getKey());
        user.ifPresent(it -> {
            List<CartRow> rows = event.getOldValue();
            rows.forEach(row -> row.setUser(it));
            cartRowrepository.saveAll(rows);
        });
    }
}
