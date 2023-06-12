package com.example.bookstoreserver.service;

import com.example.bookstoreserver.dto.cart.CartDto;
import com.example.bookstoreserver.dto.cart.CartRequest;
import com.example.bookstoreserver.model.Cart;
import com.example.bookstoreserver.model.CartItem;
import com.example.bookstoreserver.model.Product;
import com.example.bookstoreserver.repositories.CartItemRepository;
import com.example.bookstoreserver.repositories.CartRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    public CartDto getCartByUserId(HttpServletRequest request);
public void updateQuantity(Long userId, Long productId, Integer quantity);
    public void addToCart(Long userId, Long productId, Integer quantity);

    public void removeFromCart(Long cartItemId);
}
