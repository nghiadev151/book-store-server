package com.example.bookstoreserver.service;

import com.example.bookstoreserver.dto.ProductRequest;
import com.example.bookstoreserver.model.Author;
import com.example.bookstoreserver.model.Product;
import com.example.bookstoreserver.model.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    public Product getProductById(Long productId);
    public Product saveProduct(ProductRequest productRequest);
    public Page<Product> getAllProduct(Pageable pageable);
//    public List<Product> getAll();
    public void deleteProductById(Long productId);
    public Product updateProduct(Long id, ProductRequest productRequest);
    List<Product> getBestSeller();
    List<Product> getNewArrivals();
    public List<Product> search(String name);
    List<Product> filterProducts(Publisher publisher, Author author, Double minPrice, Double maxPrice);
}
