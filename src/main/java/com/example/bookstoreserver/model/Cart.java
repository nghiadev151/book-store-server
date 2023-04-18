package com.example.bookstoreserver.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "cart")
public class Cart {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<CartItem> cartItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Cart(Long id, User user, List<CartItem> cartItems) {
        this.id = id;
        this.user = user;
        this.cartItems = cartItems;
    }

    public Cart() {

    }

    // getters, setters, and constructors

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
            return cartItems;
        }

        public void setCartItems(List<CartItem> cartItems) {
            this.cartItems = cartItems;
        }



        public void addCartItem(CartItem cartItem) {
            cartItems.add(cartItem);
            cartItem.setCart(this);
        }

        public void removeCartItem(CartItem cartItem) {
            cartItems.remove(cartItem);
            cartItem.setCart(null);
        }

        public Double getTotalPrice() {
            return cartItems.stream()
                    .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                    .sum();
        }

    public CartItem getCartItemByProductId(Long productId) {
        return this.cartItems
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }
}
