package com.hazelcast.persistentcart.authentication;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String login;

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }
}
