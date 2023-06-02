package com.example.bookstoreserver.dto.cart;

import com.example.bookstoreserver.model.CartItem;
import com.example.bookstoreserver.model.Product;

public class CartItemDto {
    private Long id;
    private Integer quantity;
    private Product product;

    public CartItemDto() {
    }

    public CartItemDto(CartItem cartItem) {
        this.id = cartItem.getId();
        this.quantity = cartItem.getQuantity();
        this.product = cartItem.getProduct();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
