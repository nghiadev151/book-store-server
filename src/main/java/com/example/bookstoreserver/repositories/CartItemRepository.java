package com.example.bookstoreserver.repositories;

import com.example.bookstoreserver.model.CartItem;
import com.example.bookstoreserver.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
