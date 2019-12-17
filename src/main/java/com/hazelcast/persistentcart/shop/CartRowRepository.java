package com.hazelcast.persistentcart.shop;

import com.hazelcast.persistentcart.authentication.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CartRowRepository extends PagingAndSortingRepository<CartRow, Long> {

    List<CartRow> findAllByUser(User user);
    void deleteAllByUser(User user);
}
