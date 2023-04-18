package com.example.bookstoreserver.service;

import com.example.bookstoreserver.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {

    public Product getProductById(Long productId);
    public Product saveProduct(Product product);
    public List<Product> getAllProduct();
}
