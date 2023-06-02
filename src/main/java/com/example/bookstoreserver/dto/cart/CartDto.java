package com.example.bookstoreserver.dto.cart;

import com.example.bookstoreserver.model.CartItem;

import java.util.List;

public class CartDto {
private List<CartItemDto> cartItems ;
private double totalCost;

    public CartDto() {
    }

    public List<CartItemDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
