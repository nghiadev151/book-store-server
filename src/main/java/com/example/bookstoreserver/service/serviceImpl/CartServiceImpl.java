package com.example.bookstoreserver.service.serviceImpl;

import com.example.bookstoreserver.dto.cart.CartDto;
import com.example.bookstoreserver.dto.cart.CartItemDto;
import com.example.bookstoreserver.exception.ResourceNotFoundException;
import com.example.bookstoreserver.model.Cart;
import com.example.bookstoreserver.model.CartItem;
import com.example.bookstoreserver.model.Product;
import com.example.bookstoreserver.model.User;
import com.example.bookstoreserver.repositories.CartItemRepository;
import com.example.bookstoreserver.repositories.CartRepository;
import com.example.bookstoreserver.repositories.ProductRepository;
import com.example.bookstoreserver.repositories.UserRepository;
import com.example.bookstoreserver.service.CartService;
import com.example.bookstoreserver.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private  CartRepository cartRepository;
    @Autowired
    public JwtService jwtService;
    @Autowired
    private  CartItemRepository cartItemRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private  ProductRepository productRepository;
    @Autowired
    private  UserRepository userRepository;

    @Override
    public CartDto getCartByUserId(String token) {
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
       Cart cart =  user.getCart();
       List<CartItemDto> cartItems = new ArrayList<>();
       double totalCost = 0;
        for (CartItem c: cart.getCartItems()) {
            CartItemDto cartItemDto = new CartItemDto(c);
            totalCost += cartItemDto.getQuantity() * c.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }
        CartDto cartDto =new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);
        System.out.println(cartDto.getTotalCost());
       return cartDto;
    }
    @Override
    public void addToCart(Long userId, Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = user.getCart();


        if (cart == null) {
           cart = new Cart();

            cart.setUser(user);

            cartRepository.save(cart);

            cart.setId(cart.getId());

            user.setCart(cart);
            userRepository.save(user);


        }
        CartItem cartItem = cart.getCartItemByProductId(productId);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cart.addCartItem(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);


    }

    @Override
    public void updateQuantity(Long userId, Long productId, Integer quantity){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = user.getCart();
        CartItem cartItem = cart.getCartItemByProductId(productId);
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
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
