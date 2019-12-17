package com.hazelcast.persistentcart.authentication;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class User implements Serializable {

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
