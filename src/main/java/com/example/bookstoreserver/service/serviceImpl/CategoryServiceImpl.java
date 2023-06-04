package com.example.bookstoreserver.service.serviceImpl;

import com.example.bookstoreserver.model.Category;
import com.example.bookstoreserver.repositories.CategoryRepository;
import com.example.bookstoreserver.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
