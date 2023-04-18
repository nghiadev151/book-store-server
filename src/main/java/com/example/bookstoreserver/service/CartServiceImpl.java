package com.example.bookstoreserver.service;

import com.example.bookstoreserver.model.Cart;
import com.example.bookstoreserver.model.CartItem;
import com.example.bookstoreserver.model.Product;
import com.example.bookstoreserver.model.User;
import com.example.bookstoreserver.repositories.CartItemRepository;
import com.example.bookstoreserver.repositories.CartRepository;
import com.example.bookstoreserver.repositories.ProductRepository;
import com.example.bookstoreserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private  CartRepository cartRepository;
    @Autowired
    private  CartItemRepository cartItemRepository;
    @Autowired
    private  ProductService productService;
    @Autowired
    private  ProductRepository productRepository;
    @Autowired
    private  UserRepository userRepository;

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }
    @Override
    public Cart addToCart(Integer userId, Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = user.getCarts().stream().findFirst().orElse(new Cart());
        if (cart.getUser() == null) {
           cart.setUser(user);
        }
        CartItem cartItem = cart.getCartItemByProductId(productId);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cart.getCartItems().add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }

        cartRepository.save(cart);
        return cart;
    }
    @Override
    public void removeFromCart(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found"));
        Cart cart = cartItem.getCart();
        cart.removeCartItem(cartItem);
        cartRepository.save(cart);
    }
}
