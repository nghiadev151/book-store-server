package com.example.bookstoreserver.controller;

import com.example.bookstoreserver.model.Cart;
import com.example.bookstoreserver.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts/{cartId}")
public class CartController {
    private final CartService cartService;
@Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public Cart getCart(@PathVariable Long cartId){
        return cartService.getCartById(cartId);
    }
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long cartItemId){
        cartService.removeFromCart(cartItemId);
        return ResponseEntity.ok().build();
    }
}
