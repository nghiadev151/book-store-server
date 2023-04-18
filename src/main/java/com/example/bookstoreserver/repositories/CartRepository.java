package com.example.bookstoreserver.repositories;

import com.example.bookstoreserver.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface CartRepository extends JpaRepository<Cart, Long> {
}
