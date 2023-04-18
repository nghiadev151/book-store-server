package com.example.bookstoreserver.service;

import com.example.bookstoreserver.model.Cart;
import com.example.bookstoreserver.model.CartItem;
import com.example.bookstoreserver.model.Product;
import com.example.bookstoreserver.repositories.CartItemRepository;
import com.example.bookstoreserver.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    public Cart getCartById(Long id);

    public Cart addToCart(Integer userId, Long productId, Integer quantity);

    public void removeFromCart(Long cartItemId);
}
