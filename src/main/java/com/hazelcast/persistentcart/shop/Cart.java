package com.hazelcast.persistentcart.shop;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Cart implements Serializable, Iterable<Cart.CartRow> {

    private final List<CartRow> content = new ArrayList<>();

    public void add(Product product, int quantity) {
        Assert.isTrue(quantity > 0, "Quantity must be greater than 0");
        CartRow row = new CartRow(product);
        if (content.contains(row)) {
            int index = content.indexOf(row);
            CartRow existingRow = content.get(index);
            existingRow.quantity += quantity;
        } else {
            content.add(new CartRow(product, quantity));
        }
    }

    public void remove(Product product) {
        content.remove(new CartRow(product));
    }

    @Override
    public Iterator<CartRow> iterator() {
        return content.iterator();
    }

    public static class CartRow implements Serializable {

        private final Product product;
        private int quantity;

        CartRow(Product product, int quantity) {
            Assert.notNull(product, "Product is required");
            this.product = product;
            this.quantity = quantity;
        }

        CartRow(Product product) {
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
    }
}

