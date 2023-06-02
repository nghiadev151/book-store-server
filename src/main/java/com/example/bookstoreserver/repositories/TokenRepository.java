package com.example.bookstoreserver.repositories;

import com.example.bookstoreserver.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByRefreshToken(String refreshToken);
}
