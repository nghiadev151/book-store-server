package com.example.bookstoreserver.controller;

import com.example.bookstoreserver.model.Product;
import com.example.bookstoreserver.service.CartService;
import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts/{cartId}/items")
public class CartItemController {
    private final CartService cartService;
@Autowired
    public CartItemController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<?> addToCart(@PathVariable Integer userId, @RequestBody AddToCartRequest request){
        cartService.addToCart(userId, request.product.getId(), request.quantity);
        return ResponseEntity.ok().build();
    }
    @Data
    static class AddToCartRequest{
        private Product product;
        private Integer quantity;
    }
}
