package com.example.bookstoreserver.repositories;

import com.example.bookstoreserver.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
