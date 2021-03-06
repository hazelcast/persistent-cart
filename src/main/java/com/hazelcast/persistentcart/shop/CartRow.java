package com.hazelcast.persistentcart.shop;

import com.hazelcast.persistentcart.authentication.User;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class CartRow implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;

    private int quantity;

    public CartRow(Product product, int quantity) {
        Assert.notNull(product, "Product is required");
        this.product = product;
        this.quantity = quantity;
    }

    public CartRow(Product product) {
        this(product, 0);
    }

    /**
     * To please JPA.
     */
    public CartRow() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartRow cartRow = (CartRow) o;
        return product.equals(cartRow.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void setUser(User user) {
        this.user = user;
    }
}