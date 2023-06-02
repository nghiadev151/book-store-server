package com.example.bookstoreserver.service;

import com.example.bookstoreserver.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CategoryService {
    public List<Category> getAllCategory();
}
