package com.example.bookstoreserver.service;

import com.example.bookstoreserver.model.Product;
import com.example.bookstoreserver.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    public Product getProductById(Long productId);
}
