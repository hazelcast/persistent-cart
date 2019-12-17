package com.hazelcast.persistentcart.shop;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;

public class CartRow implements Serializable {

    private final Product product;
    private int quantity;

    public CartRow(Product product, int quantity) {
        Assert.notNull(product, "Product is required");
        this.product = product;
        this.quantity = quantity;
    }

    public CartRow(Product product) {
        this(product, 0);
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
}